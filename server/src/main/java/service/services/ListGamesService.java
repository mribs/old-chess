package service.services;

import dataAccess.DAO.GameDAO;
import dataAccess.models.Game;
import service.requests.ListGamesRequest;
import service.results.ListGamesResult;

import java.util.List;

public class ListGamesService {
  public ListGamesResult listGames(ListGamesRequest listGamesRequest){
    GameDAO gameDAO = new GameDAO();
    List<Game> gameList= gameDAO.getGames();
    return new ListGamesResult(gameList);
  }
}
