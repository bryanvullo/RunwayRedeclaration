package uk.ac.soton.comp2211.control;

import javafx.fxml.Initializable;

import com.mongodb.client.MongoClient;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import uk.ac.soton.comp2211.model.Database;
import uk.ac.soton.comp2211.model.User;

public class ManageUsers implements Initializable {
  @FXML
  private BorderPane borderPane = new BorderPane();
  @FXML
  private VBox cardsVBox = new VBox();
  @FXML
  private ListView allUsersListView = new ListView();
  @FXML
  private Label usernameLabel;
  @FXML
  private Label statusLabel;
  @FXML
  private Label roleLabel;
  @FXML
  private SplitMenuButton changeRoleButton = new SplitMenuButton();
  @FXML
  private ListView unAuthorisedUsersListView = new ListView();
  @FXML
  private ListView authorisedUsersListView = new ListView();
  @FXML
  private Button grantAccessButton = new Button();
  @FXML
  private TabPane usersTabPane = new TabPane();
  private MongoClient mongoClient;
  private String username;
  private User.AccessLevel toChangeRole;

  public void grant() {
    User user = Database.getUser(this.username);
    user.setAccessStatus(User.AccessStatus.AUTHORISED);
    Database.updateUser(user);
    unAuthorisedUsersListView.getItems().remove(this.username);
    authorisedUsersListView.getItems().add(this.username);
  }

  public void updateUser() {
    User user = Database.getUser(this.username);
    this.usernameLabel.setText("Username: " + user.getUsername());
    this.statusLabel.setText("Status: " + user.getAccessStatus().toString());
    this.roleLabel.setText("Role: " + user.getAccessLevel().toString());
    if (user.getAccessStatus() == User.AccessStatus.UNAUTHORISED) {
      this.grantAccessButton.setVisible(true);
    } else {
      this.grantAccessButton.setVisible(false);
    }

    MenuItem item = (MenuItem) this.changeRoleButton.getItems().get(0);
    item.setOnAction((event) -> {
      this.toChangeRole = User.AccessLevel.ADMIN;
      Label var10000 = this.roleLabel;
      String var10001 = user.getAccessLevel().toString();
      var10000.setText("Role: " + var10001 + ">>" + User.AccessLevel.ADMIN.toString());
    });
    item = (MenuItem) this.changeRoleButton.getItems().get(1);
    item.setOnAction((event) -> {
      this.toChangeRole = User.AccessLevel.EDITOR;
      Label var10000 = this.roleLabel;
      String var10001 = user.getAccessLevel().toString();
      var10000.setText("Role: " + var10001 + ">>" + User.AccessLevel.EDITOR.toString());
    });
    item = (MenuItem) this.changeRoleButton.getItems().get(2);
    item.setOnAction((event) -> {
      this.toChangeRole = User.AccessLevel.VIEWER;
      Label var10000 = this.roleLabel;
      String var10001 = user.getAccessLevel().toString();
      var10000.setText("Role: " + var10001 + ">>" + User.AccessLevel.VIEWER.toString());
    });
  }

  public void changeRole() {
    User user = Database.getUser(this.username);
    if (this.toChangeRole != null) {
      user.setAccessLevel(this.toChangeRole);
      if (Database.updateUser(user)) {
        this.roleLabel.setText("Role: " + user.getAccessLevel().toString());
        this.toChangeRole = null;
      } else {
        Label var10000 = this.roleLabel;
        String var10001 = user.getAccessLevel().toString();
        var10000.setText("Role: " + var10001 + ">>" + this.toChangeRole.toString());
      }
    }

  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    ArrayList<User> users = Database.getAllUsers();

    for (User user : users) {
      allUsersListView.getItems().add(user.getUsername());
      if (user.getAccessStatus() == User.AccessStatus.UNAUTHORISED) {
        unAuthorisedUsersListView.getItems().add(user.getUsername());
      } else {
        authorisedUsersListView.getItems().add(user.getUsername());
      }
    }

    allUsersListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        username = newValue.toString();
        updateUser();
      }
    });
    authorisedUsersListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        this.username = newValue.toString();
        this.updateUser();
      }
    });
    unAuthorisedUsersListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        this.username = newValue.toString();
        this.updateUser();
      }
    });
    changeRoleButton.getItems().clear();
    changeRoleButton.getItems().addAll(new MenuItem[]{new MenuItem("ADMIN"), new MenuItem("EDITOR"), new MenuItem("VIEWER")});
    usersTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        if (newValue.getText().equals("All Users")) {
          if (this.allUsersListView.getSelectionModel().getSelectedItem() == null) {
            this.allUsersListView.getSelectionModel().select(0);
          }

          this.username = this.allUsersListView.getSelectionModel().getSelectedItem().toString();
          this.updateUser();
        } else if (newValue.getText().equals("Authorised Users")) {
          if (this.authorisedUsersListView.getSelectionModel().getSelectedItem() == null) {
            this.authorisedUsersListView.getSelectionModel().select(0);
          }

          this.username = this.authorisedUsersListView.getSelectionModel().getSelectedItem().toString();
          this.updateUser();
        } else {
          if (this.unAuthorisedUsersListView.getSelectionModel().getSelectedItem() == null) {
            this.unAuthorisedUsersListView.getSelectionModel().select(0);
          }

          this.username = this.unAuthorisedUsersListView.getSelectionModel().getSelectedItem().toString();
          this.updateUser();
        }

      }
    });
    this.username = this.allUsersListView.getItems().get(0).toString();
    this.allUsersListView.getSelectionModel().select(0);
    this.updateUser();
  }
}
