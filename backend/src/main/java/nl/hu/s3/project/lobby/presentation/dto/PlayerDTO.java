package nl.hu.s3.project.lobby.presentation.dto;

import nl.hu.s3.project.lobby.domain.Player;

public record PlayerDTO(String username, String fullname, String email) {

    public static PlayerDTO from(Player player) {
        return new PlayerDTO(player.getUsername(), player.getFullName(), player.getEmail());
    }
}
