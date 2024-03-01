package service.services;

import dataAccess.DAO.GameDAO;
import dataAccess.DataAccessException;
import dataAccess.models.Game;
import service.requests.CreateGameRequest;
import service.results.CreateGameResult;

public class CreateGameService {
  public CreateGameResult createGame(CreateGameRequest request) throws DataAccessException{
    GameDAO gameDAO = new GameDAO();
    //create game if authToken in header is valid
    Game game = gameDAO.insert(request.getGameName());
    return new CreateGameResult(game.getGameID());
  }
}
