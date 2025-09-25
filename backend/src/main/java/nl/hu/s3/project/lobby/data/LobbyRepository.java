package nl.hu.s3.project.lobby.data;

import nl.hu.s3.project.lobby.domain.Lobby;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LobbyRepository extends JpaRepository<Lobby, Long> {
}
