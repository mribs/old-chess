package dataAccess.DAO.SQL;

import com.google.gson.Gson;
import dataAccess.*;
import dataAccess.models.User;

import java.sql.SQLException;


public class UserDAO extends DAO {
  //creates new user
  public void createUser(User user) throws AlreadyTakenException, BadRequestException, DataAccessException {
    if (user == null || user.getUsername() == null) throw new BadRequestException();
// TODO implement ->   if (TempDatabase.userMap.containsKey(u.getUsername()) ) throw new AlreadyTakenException();
    var statement = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
    var json = new Gson().toJson(user);
    var id = executeUpdate(statement, user.getUsername(), user.getEmail(), user.getPassword(), json);

  }

  //returns user information
  public User readUser(String userName) throws DataAccessException {
    if (userName == null) throw new DataAccessException("userName must not be null");
    try (var conn=DatabaseManager.getConnection()) {
      var statement="SELECT username, json FROM user WHERE username=?";
      try (var ps=conn.prepareStatement(statement)) {
        try (var rs=ps.executeQuery()) {
          var username=rs.getString("username");
          var json=rs.getString("json");
          var user=new Gson().fromJson(json, User.class);
          return user;
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  //updates user information
//  void updateUser(User u) throws DataAccessException{
//  }

  //deletes user
  void deleteUser(User u) throws  DataAccessException {
    TempDatabase.userMap.remove(u);
  }

  //clear users
  public void clearUsers() {
    TempDatabase.userMap.clear();
  }
}
