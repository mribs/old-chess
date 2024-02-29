package service.results;

import dataAccess.models.Game;

import java.util.ArrayList;
import java.util.List;

public class ListGamesResult {
  List<Game> games = new ArrayList<>();

  public ListGamesResult(List<Game> games) {
    this.games=games;
  }
}
