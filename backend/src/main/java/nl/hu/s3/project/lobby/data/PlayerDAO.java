package nl.hu.s3.project.lobby.data;

import jakarta.persistence.EntityManager;
import nl.hu.s3.project.lobby.domain.Player;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerDAO {

    private final EntityManager entityManager;

    public PlayerDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Player> selectPlayers() {
        return entityManager.createQuery("SELECT p FROM LobbyPlayer p", Player.class).getResultList();
    }

    public void insertPlayer(Player player) {
        entityManager.persist(player);
    }

    public void updatePlayer(String username, Player updatedPlayer) {
        Player found = selectPlayer(username);
        if (found == null) {
            throw new RuntimeException("Player not found");
        }
        found.setFullName(updatedPlayer.getFullName());
        found.setEmail(updatedPlayer.getEmail());
    }

    public void deletePlayer(Player found) {
        entityManager.remove(found);
    }

    public Player selectPlayer(String username) {
        return entityManager.find(Player.class, username);
    }
}
