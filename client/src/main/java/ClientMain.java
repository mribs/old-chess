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

        new MainUI(serverUrl).run();

    }
}