package uk.ac.soton.comp2211.model;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import javafx.scene.control.Alert;
import org.bson.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

import static com.mongodb.client.model.Filters.eq;

public class Database {

  private static final String connectionString = "mongodb+srv://admin:admin@runwayredeclaration.oa7f9gt.mongodb.net/?retryWrites=true&w=majority";

  private static final Logger logger = Logger.getLogger(Database.class.getName());

  private static MongoClient mongoClient;

  public Database() {
    Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
    try (MongoClient mongoClient = MongoClients.create(connectionString)) {
      Database.mongoClient = mongoClient;
      logger.log(Level.INFO, "Connected to the database");
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Failed to connect to the database");
    }
  }
  public Database(MongoClient mongoClient) {
    Database.mongoClient = mongoClient;
  }

  public static void insertUser(String username, String password, String accessLevel) {
    MongoDatabase database = mongoClient.getDatabase("UserDatabase");
    MongoCollection<Document> users = database.getCollection("users");
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String encryptedPass = encoder.encode(password);
    Document user = new Document("username", username).append("password", encryptedPass).append("access_level", accessLevel).append("accessStatus", User.AccessStatus.UNAUTHORISED.toString());
    if (userExists(username)) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setContentText("User Already exists! please choose different username or contact admin to change password.");
      alert.show();
      logger.log(Level.WARNING, "User already exists");
    } else {
      InsertOneResult result = users.insertOne(user);
      if (result.wasAcknowledged()) {
        logger.log(Level.INFO, "User inserted successfully");
      } else {
        logger.log(Level.WARNING, "User insertion failed");
      }
    }
  }

  public static void insertUser(User user) {
    MongoDatabase database = mongoClient.getDatabase("UserDatabase");
    MongoCollection<Document> users = database.getCollection("users");
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String encryptedPass = encoder.encode(user.getPassword());
    Document userDocument = new Document("username", user.getUsername()).append("password", encryptedPass).append("access_level", user.getAccessLevel());
    if (userExists(user.getUsername())) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setContentText( user.getUsername() + " username Already exists! please choose different username or contact admin to change password.");
      alert.show();
      logger.log(Level.WARNING, user.getUsername() + " already exists");
    } else {
      InsertOneResult result = users.insertOne(userDocument);
      if (result.wasAcknowledged()) {
        logger.log(Level.INFO, "User inserted successfully");
      } else {
        logger.log(Level.WARNING, "User insertion failed");
      }
    }
  }

  public static boolean userExists(String username) {
    try {
      MongoDatabase database = mongoClient.getDatabase("UserDatabase");
      MongoCollection<Document> users = database.getCollection("users");
      return users.find(Filters.eq("username", username)).first() != null;
    } catch (Exception e) {
      logger.log(Level.WARNING, "Error: Checking user existence: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public static boolean updateUser(User user){
    MongoDatabase database = mongoClient.getDatabase("UserDatabase");
    MongoCollection<Document> users = database.getCollection("users");
    Document query = new Document("username", user.getUsername());
    Document update = new Document("$set", (new Document("access_level", user.getAccessLevel().toString())).append("accessStatus", user.getAccessStatus().toString()));
    users.updateOne(query, update);
    if (users.getWriteConcern().isAcknowledged()) {
      logger.log(Level.INFO, "User updated successfully");
      return true;
    } else {
      logger.log(Level.WARNING, "User update failed");
      return false;
    }
  }

  public boolean updatePassword(String username, String newPassword){
    MongoDatabase database = mongoClient.getDatabase("UserDatabase");
    MongoCollection<Document> users = database.getCollection("users");
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String encryptedPass = encoder.encode(newPassword);
    Document query = new Document("username", username);
    Document update = new Document("$set", new Document("password", encryptedPass));
    users.updateOne(query, update);
    if (users.getWriteConcern().isAcknowledged()) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setContentText("Password updated successfully!");
      alert.show();
      logger.log(Level.INFO, "Password updated successfully");
      return true;
    } else {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setContentText("Error: Password was not Updated!");
      alert.show();
      logger.log(Level.WARNING, "Password update failed");
      return false;
    }
  }

  public static boolean checkPassword(String username, String password) {
    MongoDatabase database = mongoClient.getDatabase("UserDatabase");
    MongoCollection<Document> users = database.getCollection("users");
    Document query = new Document("username", username);
    Document user = users.find(query).first();
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    assert user != null;
    logger.log(Level.INFO, "Checking password for user: " + user.getString("username"));
    return encoder.matches(password, user.getString("password"));
  }

  public static User getUser(String username) {
    MongoDatabase database = mongoClient.getDatabase("UserDatabase");
    MongoCollection<Document> users = database.getCollection("users");
    Document query = new Document("username", username);
    Document user = users.find(query).first();

    assert user != null;

    User.AccessStatus accessStatus = user.getString("accessStatus") != null ? User.AccessStatus.valueOf(user.getString("accessStatus")) : User.AccessStatus.AUTHORISED;
    return new User(user.getString("username"), user.getString("password"), User.AccessLevel.valueOf(user.getString("access_level")), accessStatus);
  }


  public void requestAccess(String username) {
    MongoDatabase database = mongoClient.getDatabase("UserDatabase");
    MongoCollection<Document> users = database.getCollection("access-requests");
    Document request = new Document("username", username);
    users.insertOne(request);
  }


  public Object getAccessLevel(String username) {
    MongoDatabase database = mongoClient.getDatabase("UserDatabase");
    MongoCollection<Document> users = database.getCollection("users");
    Document query = new Document("username", username);
    Document user = users.find(query).first();
    return user != null ? user.getString("access_level") : null;
  }


  public static ArrayList<User> getAllUsers() {
    ArrayList<User> usersList = new ArrayList<>();

    try {
      MongoDatabase database = mongoClient.getDatabase("UserDatabase");
      MongoCollection<Document> users = database.getCollection("users");
      users.find().forEach((document) -> {
        String username = document.getString("username");
        String password = document.getString("password");
        String AccessLevel = document.getString("access_level");
        String status = document.getString("accessStatus");
        usersList.add(new User(username, password, User.AccessLevel.valueOf(AccessLevel), User.AccessStatus.valueOf(status)));
      });
    } catch (Exception var4) {
      logger.log(Level.SEVERE, "Error fetching all users: " + var4.getMessage());
      var4.printStackTrace();
    }

    return usersList;
  }

  public static String getConnectionString() {
    return connectionString;
  }

  public void close() {
    mongoClient.close();
  }
}

