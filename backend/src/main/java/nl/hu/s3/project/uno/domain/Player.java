package nl.hu.s3.project.uno.domain;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import nl.hu.s3.project.uno.data.CardListConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {

    private String username;
    private List<Card> hand = new ArrayList<>();

    public Player(String username) {
        this.username = username;
    }
    public Player(String username, List<Card> hand) {
        this.username = username;
        this.hand = new ArrayList<>(hand);
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
}
