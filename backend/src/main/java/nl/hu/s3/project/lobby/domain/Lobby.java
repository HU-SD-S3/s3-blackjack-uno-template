package nl.hu.s3.project.lobby.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Lobby {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private AvailableGame game;

    @ManyToOne
    private Player host;
    @ManyToMany
    private List<Player> players = new ArrayList<>();
    @ManyToMany
    private List<Player> playersThatStartedGame = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Gamestate gamestate = Gamestate.INLOBBY;


    protected Lobby() {
    }
    public Lobby(AvailableGame game, Player host) {
        if (host == null) {
            throw new NullPointerException("Host cannot be null");
        }
        if (game == null) {
            throw new NullPointerException("Game cannot be null");
        }
        this.game = game;
        this.host = host;
        this.players.add(host);
    }

    public AvailableGame getGame() {
        return game;
    }

    public Player getHost() {
        return host;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public void join(Player player) {
        switch (this.gamestate) {
            case INLOBBY:
                if (!this.players.contains(player)) {
                    this.players.add(player);
                }
                break;
            case INGAME:
                if (this.playersThatStartedGame.contains(player)) {
                    this.players.add(player);
                } else {
                    throw new LobbyException("Game has already started");
                }
                return;
            case FINISHED:
                throw new LobbyException("Game has already finished");
            default:
        }
    }

    public void leave(Player player) {
        this.players.remove(player);
    }

    public Gamestate getGamestate() {
        return gamestate;
    }

    public void startGame(Player playerPressingButton) {
        if(playerPressingButton != this.host) {
            throw new LobbyException("Only the host can start the game");
        }
        this.playersThatStartedGame = Collections.unmodifiableList(this.players);
        this.gamestate = Gamestate.INGAME;
    }

    public void endGame() {
        this.gamestate = Gamestate.FINISHED;
    }

    public void changeGame(AvailableGame newGame) {
        this.game = newGame;
    }

    public Long getId() {
        return id;
    }
}
