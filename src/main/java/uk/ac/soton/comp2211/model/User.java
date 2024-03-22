package uk.ac.soton.comp2211.model;

public class User {
  private final String username;
  private final String password;
  private AccessLevel accessLevel;
  private AccessStatus accessStatus;

  public User(String username, String password, AccessLevel access_level, AccessStatus accessStatus) {
    this.username = username;
    this.password = password;
    this.accessLevel = access_level;
    this.accessStatus = accessStatus;
  }

  public void setAccessStatus(AccessStatus accessStatus) {
    this.accessStatus = accessStatus;
  }

  public void setAccessLevel(AccessLevel access_level) {
    this.accessLevel = access_level;
  }


  public AccessStatus getAccessStatus() {
    return accessStatus;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }


  public AccessLevel getAccessLevel() {
    return accessLevel;
  }

  public static enum AccessLevel {
    ADMIN,
    EDITOR,
    VIEWER;
  }

  public static enum AccessStatus {
    AUTHORISED,
    UNAUTHORISED;
  }
}
