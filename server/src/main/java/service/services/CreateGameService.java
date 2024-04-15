package service.services;

import exceptions.BadRequestException;
import dataAccess.DAO.SQL.GameDAO;
import exceptions.DataAccessException;
import models.Game;
import service.requests.CreateGameRequest;
import service.results.CreateGameResult;

public class CreateGameService {
  public CreateGameResult createGame(CreateGameRequest request) throws DataAccessException, BadRequestException {
   if (request.getGameName() == "" || request.getGameName() == null) {
     throw new BadRequestException();
   }
    GameDAO gameDAO = new GameDAO();

    Game game = gameDAO.insert(request.getGameName());
    return new CreateGameResult(game.getGameID());
  }
}
