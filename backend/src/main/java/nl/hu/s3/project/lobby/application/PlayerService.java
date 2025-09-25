package nl.hu.s3.project.lobby.application;

import jakarta.transaction.Transactional;
import nl.hu.s3.project.lobby.data.LobbyRepository;
import nl.hu.s3.project.lobby.data.PlayerDAO;
import nl.hu.s3.project.lobby.domain.Player;
import nl.hu.s3.project.lobby.presentation.dto.PlayerDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class PlayerService {

    private final PlayerDAO playerDAO;
    private final LobbyRepository lobbyRepository;

    public PlayerService(PlayerDAO playerDAO, LobbyRepository lobbyRepository) {
        this.playerDAO = playerDAO;
        this.lobbyRepository = lobbyRepository;
    }
    public PlayerDTO createPlayer(String name, String fullname, String email) {
        this.playerDAO.insertPlayer(new Player(name, fullname, email));
        return new PlayerDTO(name, fullname, email);
    }

    public List<PlayerDTO> getPlayers() {
        List<Player> players  = this.playerDAO.selectPlayers();
        List<PlayerDTO> dtos = new ArrayList<>();
        for(Player p : players) {
            PlayerDTO dto = new PlayerDTO(p.getUsername(), p.getFullName(), p.getEmail());
            dtos.add(dto);
        }
        return dtos;
    }

    public PlayerDTO updatePlayer(String username, String fullname, String email) {
        Player found = this.playerDAO.selectPlayer(username);
        found.setFullName(fullname);
        found.setEmail(email);

        this.playerDAO.updatePlayer(username, found);
        return new PlayerDTO(found.getUsername(), found.getFullName(), found.getEmail());
    }

    public void deletePlayer(String username) {
        Player found = this.playerDAO.selectPlayer(username);
        this.playerDAO.deletePlayer(found);
    }

    public PlayerDTO getPlayer(String username) {
        Player found = this.playerDAO.selectPlayer(username);
        return new PlayerDTO(found.getUsername(), found.getFullName(), found.getEmail());
    }
}
