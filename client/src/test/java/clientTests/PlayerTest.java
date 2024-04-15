package clientTests;

import org.junit.jupiter.api.Test;
import ui.Player;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
  private Player player = new Player();

  @Test
  void helpTest() {
    String logoutHelp =player.help();
    System.out.println(logoutHelp);

    player.setLoggedIn(true);
    String loginHelp = player.help();
    System.out.println(loginHelp);

    assertNotEquals(logoutHelp, loginHelp);
  }


}