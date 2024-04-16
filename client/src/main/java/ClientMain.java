import exceptions.DataAccessException;
import ui.Game.GameBoard;
import ui.Game.GamePlayUI;
import ui.MainUI;

public class ClientMain {
    public static void main(String[] args) {
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }

//        new MainUI(serverUrl).run();

        //TODO: temporary for skipping to gamePlay UI
        try {
            new GamePlayUI(new GameBoard(serverUrl), (serverUrl)).run();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}