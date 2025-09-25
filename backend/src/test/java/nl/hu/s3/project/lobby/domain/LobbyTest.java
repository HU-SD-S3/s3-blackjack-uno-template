package nl.hu.s3.project.lobby.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LobbyTest {

    private Player host;
    private Lobby game;


    @BeforeEach
    public void setUp() {
        host = new Player("host");
        game = new Lobby(AvailableGame.BLACKJACK, host);
    }

    @Test
    public void canJoinGameInLobby() {
        Player someoneElse = new Player("somoneElse");
        game.join(someoneElse);

        assertEquals(2, game.getPlayers().size());
        assertTrue(game.getPlayers().contains(someoneElse));
    }

    @Test
    public void onlyHostCanStartGame() {
        Player someoneElse = new Player("somoneElse");
        game.join(someoneElse);

        assertThrows(LobbyException.class, () -> game.startGame(someoneElse));
        assertEquals(Gamestate.INLOBBY, game.getGamestate());

        game.startGame(host);
        assertEquals(Gamestate.INGAME, game.getGamestate());
    }

    @Test
    public void canNotJoinTwice() {
        Player someoneElse = new Player("somoneElse");
        game.join(someoneElse);
        int playersCurrentlyInGame = game.getPlayers().size();

        game.join(someoneElse);
        assertEquals(playersCurrentlyInGame, game.getPlayers().size());
    }
}