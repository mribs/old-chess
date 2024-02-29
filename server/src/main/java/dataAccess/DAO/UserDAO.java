package dataAccess.DAO;

import dataAccess.AlreadyTakenException;
import dataAccess.BadRequestException;
import dataAccess.DataAccessException;
import dataAccess.models.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserDAO {
  private static Map<String, User> userMap= new ConcurrentHashMap<>();
  //creates new user
  public void createUser(User u) throws DataAccessException, AlreadyTakenException, BadRequestException {
    if (u == null || u.getUsername() == null) throw new BadRequestException();
    if (userMap.containsKey(u.getUsername()) ) throw new AlreadyTakenException();

    userMap.put(u.getUsername(), u);
  }

  //returns user information
  public User readUser(String userName) throws DataAccessException {
    if (userName == null) throw new DataAccessException("userName must not be null");

    return userMap.get(userName);
  }

  //updates user information
//  void updateUser(User u) throws DataAccessException{
//  }

  //deletes user
  void deleteUser(User u) throws  DataAccessException {
    userMap.remove(u);
  }

  //clear users
  public void clearUsers() {
    userMap.clear();
  }
}
