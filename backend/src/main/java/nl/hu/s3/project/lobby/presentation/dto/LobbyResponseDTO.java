package nl.hu.s3.project.lobby.presentation.dto;

import nl.hu.s3.project.lobby.domain.Lobby;
import nl.hu.s3.project.lobby.domain.Player;

import java.util.ArrayList;
import java.util.List;

public record LobbyResponseDTO(long id, String game, String state, List<String> players) {
    public static LobbyResponseDTO fromLobby(Lobby lobby) {
        List<String> playerNames = new ArrayList<>();
        for (Player player : lobby.getPlayers()) {
            playerNames.add(player.getUsername());
        }
        return new LobbyResponseDTO(lobby.getId(), lobby.getGame().toString(), lobby.getGamestate().toString(), playerNames);
    }
}
