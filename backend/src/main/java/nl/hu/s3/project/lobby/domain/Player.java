package nl.hu.s3.project.lobby.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity(name="LobbyPlayer")
public class Player {
    @Id
    private String username;
    private String fullName;
    private String email;

    public Player(){}

    public Player(String username) {
        this.username = username;
    }

    public Player(String username, String fullName, String email) {
        this(username);
        this.fullName = fullName;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                '}';
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Player player)) return false;
        return Objects.equals(username, player.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }

}
