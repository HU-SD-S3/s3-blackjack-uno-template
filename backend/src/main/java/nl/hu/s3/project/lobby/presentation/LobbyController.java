package nl.hu.s3.project.lobby.presentation;

import jakarta.transaction.Transactional;
import nl.hu.s3.project.lobby.data.LobbyRepository;
import nl.hu.s3.project.lobby.data.PlayerDAO;
import nl.hu.s3.project.lobby.domain.AvailableGame;
import nl.hu.s3.project.lobby.domain.Lobby;
import nl.hu.s3.project.lobby.domain.Player;
import nl.hu.s3.project.lobby.presentation.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Transactional
@RequestMapping("/lobbies")
public class LobbyController {

    private final LobbyRepository lobbies;
    private final PlayerDAO players;


    public LobbyController(LobbyRepository lobbies, PlayerDAO players){
        this.lobbies = lobbies;
        this.players = players;
    }

    private Lobby findLobbyOr404(long id) {
        Lobby lobby = lobbies.findById(id).orElse(null);
        if (lobby == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lobby not found");
        }
        return lobby;
    }

    @GetMapping
    public List<LobbyResponseDTO> getLobbies() {
        List<LobbyResponseDTO> responses = new ArrayList<>();
        List<Lobby> lobbies = this.lobbies.findAll();
        for (Lobby lobby : lobbies) {
            responses.add(LobbyResponseDTO.fromLobby(lobby));
        }

        return responses;
    }

    @PostMapping
    public ResponseEntity<LobbyResponseDTO> createLobby(@RequestBody CreateLobbyDTO lobbyDTO) {
        Lobby newLobby = new Lobby(AvailableGame.valueOf(lobbyDTO.game()), new Player(lobbyDTO.host()));
        lobbies.save(newLobby);
        return ResponseEntity
                .created(URI.create("/lobbies/" + newLobby.getId()))
                .body(LobbyResponseDTO.fromLobby(newLobby));
    }

    @GetMapping("{id}")
    public LobbyResponseDTO getLobby(@PathVariable int id) {
        Lobby found = findLobbyOr404(id);
        return LobbyResponseDTO.fromLobby(found);
    }

    @PutMapping("{id}")
    //Door een DTO te gebruiken kunnen we een bewuste keuze maken wat wel/niet aanpasbaar mag zijn.
    public LobbyResponseDTO replaceLobby(@PathVariable int id, @RequestBody ChangeGameDTO game) {
        Lobby found = findLobbyOr404(id);
        AvailableGame newGame = AvailableGame.valueOf(game.game());

        found.changeGame(newGame);

        return LobbyResponseDTO.fromLobby(found);
    }

    @DeleteMapping("{id}")
    public void removeLobby(@PathVariable int id) {
        Lobby lobby = findLobbyOr404(id);
        lobbies.delete(lobby);
    }

    @PostMapping("/{id}/players")
    public ResponseEntity<PlayerDTO> joinLobby(@PathVariable int id, @RequestBody PlayerDTO player) throws URISyntaxException {
        Lobby found = findLobbyOr404(id);
        Player foundPlayer = players.selectPlayer(player.username());
        if(foundPlayer == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Player not found");
        }

        found.join(foundPlayer);
        return ResponseEntity
                .created(new URI("/lobbies/" + id + "/players/" + foundPlayer.getUsername()))
                .body(PlayerDTO.from(foundPlayer));
    }

    //Omdat we nu de username als url gebruiken zou het ook met een PUT kunnen:
    @PutMapping("/{id}/players/{username}")
    public PlayerDTO joinLobby2(@PathVariable int id, @PathVariable String username) throws URISyntaxException {
        Lobby found = findLobbyOr404(id);
        Player foundPlayer = players.selectPlayer(username);
        if(foundPlayer == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Player not found");
        }

        found.join(foundPlayer);
        return PlayerDTO.from(foundPlayer);
    }

    private Player findPlayerOr404(int lobbyId, String username) {
        Lobby lobby = findLobbyOr404(lobbyId);
        for (Player player : lobby.getPlayers()) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
    }

    @GetMapping("/{id}/players/{username}")
    public PlayerDTO getPlayer(@PathVariable int id, @PathVariable String username) {
        Player foundPlayer = findPlayerOr404(id, username);
        return PlayerDTO.from(foundPlayer);
    }

    @GetMapping("/{id}/players")
    public List<PlayerDTO> getPlayers(@PathVariable int id) {
        Lobby found = findLobbyOr404(id);
        List<PlayerDTO> players = new ArrayList<>();
        for (Player player : found.getPlayers()) {
            players.add(PlayerDTO.from(player));
        }
        return players;
    }

    @DeleteMapping("/{id}/players/{username}")
    public void leaveLobby(@PathVariable int id, @PathVariable String username) {
        Lobby found = findLobbyOr404(id);
        Player foundPlayer = findPlayerOr404(id, username);
        found.leave(foundPlayer);
    }

    @PostMapping("/{id}/start")
    public LobbyResponseDTO startGame(@PathVariable int id, @RequestBody Player player) {
        Lobby found = findLobbyOr404(id);
        found.startGame(player);
        return LobbyResponseDTO.fromLobby(found);
    }
}
