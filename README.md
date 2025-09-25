# Opdrachtdag Uno

## Inleiding

Welkom bij de eerste opdracht-dag. Het idee is dat we onszelf uitdagen om in 1 dag zo ver mogelijk te komen, door een backend-service te bouwen voor een
bepaald spel.

We verwachten dat je:

* Je correct gebruik maakt van een ORM, resulterend in een goed datamodel.
* Je Resource-Oriented endpoints definiëert, met de juiste HTTP methoden, URLs en statussen.
* Je zo goed mogelijk gebruik maakt van de 'pilaren van OOP' (Abstractie, Encapsulation, Modularity & Hierarchy) om een solide domein model te maken.
* Je houdt aan de behandelde 4-lagen architectuur (Presentation, Application, Domain, Data) en goed ge-encapsuleerde componenten hanteert.
* Je Spring als Framework gebruikt zoals het bedoeld is.

## Uno

Deze opdrachtdag gaan we een Backend-service voor Uno programmeren. Zie voor de regels: [https://www.unorules.org/](https://www.unorules.org/wp-content/uploads/2021/03/Uno-Rules-PDF-Official-Rules-unorules.org_.pdf)

De aangeleverde startcode is een welgemeende poging om je op weg te helpen. Als je op enig punt denkt "Nee, dit wil ik anders",
dan staat je dat volledig vrij. Voel je dus vrij de aangeleverde relatie tussen Game & Player anders te doen, 
er zijn altijd meerdere wegen die naar Rome leiden.

Qua database staat de in-memory H2-database voorgedefinieerd, maar er staat ook een functionele PostgreSQL configuratie klaar. Gebruik vooral wat je 
zelf het prettigst vindt. Voor deze opdracht-dag is H2 'persistent genoeg'.

In de startcode staat een UserProfileResolver klaar, voor als je een realistische mockup wilt van een Security-implementatie.
Een voorbeeld van dit gebruik staat in de Chips-component. Voor deze opdracht is het echter ook acceptabel (doch uiteraard ietsje minder netjes qua framework-gebruik) om de 
'speler-die-het-request-doet' als een expliciete parameter in de API-calls mee te geven. Een voorbeeld van deze simpelere
manier zit in de lobby-component.


### Basics

Om je wat richting te geven zou ik de volgende stappen aanraden (de volgorde is slechts een suggestie):
Deze (en verdere) stappen bespreken een volledige 4-laags backend-service, niet alleen een domein-laag.

0. (optioneel) Implementeer Uno (met alleen de 0-9 kaarten) voor 1 speler. De kans dat het spel dan ooit afloopt is
   klein, maar je kunt wel alle basisfunctionaliteit van de applicatie laten zien:
    * Spel starten
    * Kaarten spelen (en controleren of een kaart gespeeld mag worden)
    * etc..
1. Implementeer Uno met alleen de getal-kaarten (0-9), degene die als eerste alle kaarten kwijt is wint.
   Als een speler geen kaart kan spelen, dan wordt er automatisch een kaart gepakt, en doorgegaan naar de volgende speler.
   (er hoeft dus niet handmatig een 'pass' actie gepost te worden in dit geval)
2. Voeg de actie-kaarten toe (Skip, Reverse, Draw Two), en laat 'vrijwillig passen' toe. Hiermee is de basis van Uno af.
   (nouja, bijna: het deck klopt niet. Een echt uno deck heeft elke kaart dubbel, behalve de 0'en)

### Meer Uno (de diepte)

Met deze stappen heb je de basis-functionaliteit te pakken. Als je Uno zelf vollediger wil implementeren, dan kun je de
volgende stappen overwegen:

3. Voeg de puntentelling regels toe (dus er zijn meerdere ronden nodig om te winnen)
4. Voeg de veelgebruikte 'Stacking' huisregel toe:
    * Een Draw-Two laat de volgende speler nu alleen 2 kaarten pakken, niet meer ook z'n beurt overslaan
    * Als je aan de beurt bent, en er is een 'speciale kaart' (Draw Two, Skip, Reverse) gespeeld, dan mag je een
      passende (zelfde kleur OF zelfde type)
      eigen kaart spelen om jezelf te redden, en het erger te maken voor de volgende speler.
    * Stel er is een Draw Two gespeeld, dan mag je:
        * Zelf een Draw Two spelen om de volgende speler nog 2 extra kaarten te laten pakken (2,4,6 etc.)
        * Een skip spelen om jezelf over te slaan: de volgende speler moet dan kaarten pakken EN zijn beurt overslaan.
        * Een reverse spelen om de speelrichting om te keren: de vorige speler moet dan zelf de kaarten pakken
    * Skips 'stacken' wel, maar tellen max. 1 keer mee::
        * Zelf een Draw-Two spelen om de volgende speler 2 kaarten te laten pakken EN zijn beurt over te slaan
        * Een reverse spelen om de speelrichting om te keren: de vorige speler moet dan zelf zijn beurt overslaan
        * Een skip spelen om de volgende speler zijn beurt over te slaan (maar die hoeft niet 2 beurten over te slaan,
          hoeveel skips er ook in de 'stack' zitten; je slaat max. 1 beurt over)
    * De reverse mag wel stacken, dan blijft de richting gewoon heen-en-weer gaan totdat er een niet-reverse kaart
      gespeeld wordt.
5. Voeg de Wild & Wild Draw Four (zonder challenge) kaarten toe (Wilds doen mee aan evt. 'Stacking')
6. Voeg de 'Je moet Uno zeggen' regel toe: als je nog 1 kaart over hebt moet je Uno zeggen, anders moet je extra kaarten
   pakken. Je 'zegt Uno' middels een extra request voordat de volgende speler een actie heeft gedaan.
7. Voeg de 'Challenge' regel toe voor de Wild Draw Four kaarten, op een soortgelijke manier als het 'Uno zeggen'.

### Andere componenten (de breedte)

4. Om een potje Uno te spelen moeten alle spelers zich eerst in de Lobby aanmelden, pas als iedereen Ready is, en de Host een
   een start-request POST, dan is er een Uno-game beschikbaar voor de spelers.
5. De Host kan in de lobby een 'inzet' (in chips) instellen voor het spel. Alle spelers die meedoen moeten die inzet betalen, de
   winnaar krijgt de pot.
6. Chips & Lobby zijn herbruikbare componenten: we willen juist geen dependencies vanuit deze componenten naar Uno, maar
   alleen van Uno naar deze componenten. (dit is iets lastiger dan de meest intuitieve manier, waarbij Lobby gewoon een dependency)
   zou nemen op alle mogelijke spellen)