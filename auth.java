import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationSystem {
  
  // Map to store active sessions
  private Map<String, String> activeSessions;
  
  // Database connection information
  private String dbUrl;
  private String dbUser;
  private String dbPassword;
  
  public AuthenticationSystem(String dbUrl, String dbUser, String dbPassword) {
    this.dbUrl = dbUrl;
    this.dbUser = dbUser;
    this.dbPassword = dbPassword;
    
    this.activeSessions = new HashMap<>();
  }
  
  public boolean register(String username, String password) {
    // Hash the password with SHA-256 and base64 encode it
    String hashedPassword = hashAndEncodePassword(password);
    
    try {
      // Open database connection
      Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
      
      // Check if the username is already taken
      PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE username = ?");
      stmt.setString(1, username);
      ResultSet rs = stmt.executeQuery();
      rs.next();
      int count = rs.getInt(1);
      if (count > 0) {
        // Username is already taken
        return false;
      }
      
      // Insert the new user into the database
      stmt = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
      stmt.setString(1, username);
      stmt.setString(2, hashedPassword);
      stmt.executeUpdate();
      
      // Close database connection
      conn.close();
      
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
  
  public boolean login(String username, String password) {
    try {
      // Open database connection
      Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
      
      // Retrieve the user's hashed password from the database
      PreparedStatement stmt = conn.prepareStatement("SELECT password FROM users WHERE username = ?");
      stmt.setString(1, username);
      ResultSet rs = stmt.executeQuery();
      if (!rs.next()) {
        // Username not found
        return false;
      }
      String hashedPassword = rs.getString("password");
      
      // Hash and encode the entered password for comparison
      String enteredHashedPassword = hashAndEncodePassword(password);
      
      // Compare the two hashed passwords
      if (!hashedPassword.equals(enteredHashedPassword)) {
        // Passwords don't match
        return false;
      }
      
      // Generate a session token and store it in the activeSessions map
      String sessionToken = generateSessionToken();
      activeSessions.put(sessionToken, username);
      
      // Close database connection
      conn.close();
      
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
  
  public boolean logout(String sessionToken) {
    if (activeSessions.containsKey(sessionToken)) {
      activeSessions.remove(sessionToken);
      return true;
    } else {
      return false;
    }
  }
  
  private String hashAndEncodePassword(String password) {
    try {
      // Hash the password with SHA-256
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] hashedPasswordBytes = md.digest(password.getBytes());
      
      // Base64 encode the hashed password
      return Base64.getEncoder().encodeToString(hashedPasswordBytes);
    } catch (NoSuchAlgorithmException e){

    }
  }
}