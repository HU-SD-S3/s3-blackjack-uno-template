package nl.hu.s3.project.uno.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Game {
    private List<Player> players = new ArrayList<>();
    private List<Card> deck = new ArrayList<>();

    public Game(List<String> usernames, List<Card> deck) {
        Objects.requireNonNull(usernames);
        Objects.requireNonNull(deck);

        this.deck.addAll(deck);

        if (usernames.size() < 2) {
            throw new IllegalArgumentException("A game needs at least 2 players");
        }
        if (usernames.size() > 10) {
            throw new IllegalArgumentException("A game can not have more than 7 players");
        }

        for(String username: usernames) {
            Player player = new Player(username);
            players.add(player);
        }
    }

    //Met een tweede constructor als
    //public Game(Card lastCard, List<Player> players, List<Card> deck) { ... }
    //kun je een exacte toestand in het spel 'herbouwen'.
    //
    //Daar heb je in de 'echte' applicatie niets aan, maar voor Unit Tests is dat heel handig
    //Anders zul je nooit fatsoenlijk kunnen testen (want in het echte spel worden de kaarten geschud)

    public void setup() {
        Collections.shuffle(this.deck);

    }

    public void play(String username, Card card) {
        //dingen om hier bijv. te checken:
        // * is die speler wel aan de beurt?
        // * heeft die speler uberhaupt die kaart wel?
        // * etc.
    }

}
