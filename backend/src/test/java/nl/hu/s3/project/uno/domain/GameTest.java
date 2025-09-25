package nl.hu.s3.project.uno.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    public void atStartEveryoneHas7Cards() {

        Game game = new Game(List.of("freezeybob", "flyingalice", "charlieischarlie"), Card.allCards());
        game.setup();

        //assert dat elke speler 7 kaarten in de handen heeft
    }


    @Test
    public void canPlayCardByValue() {
        Game game; //Maak een game, waar je kan instellen wat de laatst-gespeelde kaart is
        //en wat de spelers in de hand hebben

        //Schrijf een test dat de speler die aan de beurt is een geldige kaart speelt, omdat het dezelfde waarde heeft

        //En assert bijv.:
        //- Dat de nieuwe 'laatst gespeelde kaart' de zojuist gekozen kaart is
        //- Dat de speler die net een kaart gespeeld heeft, nu 1 kaart minder in de hand heeft
        fail(); //en haal dit natuurlijk even weg ;)
    }

    @Test
    public void canPlayCardByColor() {
        Game game; //Maak een game, waar je kan instellen wat de laatst-gespeelde kaart is
        //en wat de spelers in de hand hebben

        //Schrijf een test dat de speler die aan de beurt is een geldige kaart speelt, omdat het dezelfde waarde heeft

        //En assert bijv.:
        //- Dat de nieuwe 'laatst gespeelde kaart' de zojuist gekozen kaart is
        //- Dat de speler die net een kaart gespeeld heeft, nu 1 kaart minder in de hand heeft
        fail(); //en haal dit natuurlijk even weg ;)
    }

    // Andere zaken om bijv. met een unit-test te maken:
    // * Kun je een beurt (vrijwillig?) overslaan
    // * Kan het spel ook ooit aflopen?
    // * Per speciale kaart (Draw-two, Skip, Reverse): dut ie het?
}