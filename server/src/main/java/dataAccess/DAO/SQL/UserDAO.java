package dataAccess.DAO.SQL;

import dataAccess.AlreadyTakenException;
import dataAccess.BadRequestException;
import dataAccess.DataAccessException;
import dataAccess.TempDatabase;
import dataAccess.models.User;


public class UserDAO {
  //creates new user
  public void createUser(User u) throws AlreadyTakenException, BadRequestException {
    if (u == null || u.getUsername() == null) throw new BadRequestException();
    if (TempDatabase.userMap.containsKey(u.getUsername()) ) throw new AlreadyTakenException();

    TempDatabase.userMap.put(u.getUsername(), u);
  }

  //returns user information
  public User readUser(String userName) throws DataAccessException {
    if (userName == null) throw new DataAccessException("userName must not be null");

    return TempDatabase.userMap.get(userName);
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
