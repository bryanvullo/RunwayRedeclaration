package uk.ac.soton.comp2211.model;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import javafx.scene.control.Alert;
import org.bson.Document;

import java.util.logging.Logger;
import java.util.logging.Level;
import static com.mongodb.client.model.Filters.eq;

public class Database {

  private static final String connectionString = "mongodb+srv://admin:admin@runwayredeclaration.oa7f9gt.mongodb.net/?retryWrites=true&w=majority";

  private final Logger logger = Logger.getLogger(Database.class.getName());

  private MongoClient mongoClient;

  public Database() {
    Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
    try (MongoClient mongoClient = MongoClients.create(connectionString)) {
      this.mongoClient = mongoClient;
      logger.log(Level.INFO, "Connected to the database");
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Failed to connect to the database");
    }
  }

  public static String getConnectionString() {
    return connectionString;
  }

  public Database(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  public void insertUser(String username, String password, String accessLevel) {
    MongoDatabase database = mongoClient.getDatabase("UserDatabase");
    MongoCollection<Document> users = database.getCollection("users");
    Document user = new Document("username", username)
        .append("password", password)
        .append("access_level", accessLevel);

    if (userExists(username)) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setContentText("Already exists!");
      alert.show();
      logger.log(Level.WARNING, "User already exists");
      return;
    }

    InsertOneResult result = users.insertOne(user);

    if (result.wasAcknowledged()) {
      logger.log(Level.INFO, "User inserted successfully");
    } else {
      logger.log(Level.WARNING, "User insertion failed");
    }
  }

  public boolean userExists(String username) {
    try {
      MongoDatabase database = mongoClient.getDatabase("UserDatabase");
      MongoCollection<Document> users = database.getCollection("users");
      return users.find(eq("username", username)).first() != null;
    } catch (Exception e) {
      System.err.println("Error checking user existence: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public boolean checkPassword(String username, String password) {
    MongoDatabase database = mongoClient.getDatabase("UserDatabase");
    MongoCollection<Document> users = database.getCollection("users");
    Document query = new Document("username", username);
    System.out.println("Query: " + query.toJson());
    Document user = users.find(query).first();
    return user != null && user.getString("password").equals(password);
  }

  public Object getAccessLevel (String username) {
    MongoDatabase database = mongoClient.getDatabase("UserDatabase");
    MongoCollection<Document> users = database.getCollection("users");
    Document query = new Document("username", username);
    Document user = users.find(query).first();
    return user != null ? user.getString("access_level") : null;
  }
}

