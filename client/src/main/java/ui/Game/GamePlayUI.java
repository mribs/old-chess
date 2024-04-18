package ui.Game;

import exceptions.DataAccessException;
import ui.EscapeSequences;
import ui.Player;

import java.net.URL;
import java.util.Scanner;

public class GamePlayUI {

  private GameClient player;

  public GamePlayUI(GameBoard gameBoard, String serverURL, String playerColor, Player player) throws DataAccessException {
    String serverUrl = serverURL;
    this.player = new GameClient(gameBoard, serverURL, playerColor, player);
  }

  public void run() {
    System.out.println("You've entered GamePlay Mode!");
    System.out.println(player.help());

    Scanner scanner = new Scanner(System.in);
    var result = "";
    while (!result.equals("quit")) {
      //FIXME: questionable rn
      System.out.println(EscapeSequences.SET_TEXT_BLINKING + "Enter Option >>");
      String line = scanner.nextLine();

      try {
        result = player.evalLine(line);
        String printResult = result;
        if (result.equals("quit")) {
          printResult = "Exiting game mode";
        }
        System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + printResult);
      } catch (Throwable e) {
        var msg = e.toString();
        System.out.println(msg);
      }
    }
    System.out.println();
  }
}
