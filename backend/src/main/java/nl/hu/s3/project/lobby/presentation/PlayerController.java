package nl.hu.s3.project.lobby.presentation;

import jakarta.transaction.Transactional;
import nl.hu.s3.project.lobby.application.PlayerService;
import nl.hu.s3.project.lobby.presentation.dto.PlayerDTO;
import nl.hu.s3.project.lobby.presentation.dto.UpdatePlayerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

import java.util.List;

@RestController
@Transactional
@RequestMapping("/players")
public class PlayerController {

    private PlayerService lobbyService;

    public PlayerController(PlayerService lobbyService) {
        this.lobbyService = lobbyService;
    }

    @GetMapping
    public List<PlayerDTO> getPlayers() {
        return this.lobbyService.getPlayers();
    }

    @PostMapping
    public ResponseEntity<PlayerDTO> addPlayer(@RequestBody PlayerDTO player) {
        PlayerDTO result = this.lobbyService.createPlayer(player.username(), player.fullname(), player.email());
        return  ResponseEntity.created(URI.create("/players/" + player.username())).body(result);
    }

    @PutMapping("/{username}")
    public PlayerDTO updatePlayer(@PathVariable String username, @RequestBody UpdatePlayerDTO player) {
        return this.lobbyService.updatePlayer(username, player.fullname(), player.email());
    }

    @DeleteMapping("/{username}")
    public void removePlayer(@PathVariable String username) {
        this.lobbyService.deletePlayer(username);
    }

    @GetMapping("/{username}")
    public PlayerDTO getPlayer(@PathVariable String username) {
        return this.lobbyService.getPlayer(username);
    }
}
