package wargame;

import java.util.ArrayList;
import java.util.List;

public class WarGame extends Game {

    private GroupOfCards mainDeck;
    private WarGamePlayer player1;
    private WarGamePlayer player2;
    private final int maxRounds = 100;

    public WarGame(String name) {
        super(name);

        mainDeck = new GroupOfCards(52);
        player1 = new WarGamePlayer("Player 1");
        player2 = new WarGamePlayer("Player 2");

        ArrayList<Player> list = new ArrayList<>();
        list.add(player1);
        list.add(player2);
        setPlayers(list);
    }

    @Override
    public void play() {
        setUpGame();

        int round = 1;
        while (round <= maxRounds && player1.hasCards() && player2.hasCards()) {
            System.out.println("=== Round " + round + " ===");
            playRound();
            round++;
        }

        declareWinner();
    }

    private void setUpGame() {
        
        mainDeck.getCards().clear();

        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] values = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};

        for (int i = 0; i < values.length; i++) {
            int rank = i + 2; 
            for (String suit : suits) {
                Card c = new Card(suit, values[i], rank);
                mainDeck.addCard(c);
            }
        }

        
        mainDeck.shuffle();

        
        boolean toP1 = true;
        while (!mainDeck.isEmpty()) {
            Card c = mainDeck.removeTopCard();
            if (toP1) {
                player1.addCard(c);
            } else {
                player2.addCard(c);
            }
            toP1 = !toP1;
        }

        System.out.println("Cards dealt. Each player has "
                + player1.cardCount() + " cards.");
    }

    private void playRound() {
        Card c1 = player1.drawTopCard();
        Card c2 = player2.drawTopCard();

        if (c1 == null || c2 == null) {
            
            return;
        }

        System.out.println(player1.getName() + " plays " + c1);
        System.out.println(player2.getName() + " plays " + c2);

        List<Card> pot = new ArrayList<>();
        pot.add(c1);
        pot.add(c2);

        int result = compareCards(c1, c2);

        if (result > 0) {
            System.out.println(player1.getName() + " wins the round.");
            collectCards(player1, pot);
        } else if (result < 0) {
            System.out.println(player2.getName() + " wins the round.");
            collectCards(player2, pot);
        } else {
            System.out.println("WAR!");
            startWar(pot);
        }

        System.out.println("Card counts: P1=" + player1.cardCount()
                + " P2=" + player2.cardCount());
    }

    private int compareCards(Card c1, Card c2) {
        return Integer.compare(c1.getRank(), c2.getRank());
    }

    private void collectCards(WarGamePlayer winner, List<Card> pot) {
        for (Card c : pot) {
            winner.addCard(c);
        }
    }

    private void startWar(List<Card> pot) {
        
        if (!player1.hasCards() || !player2.hasCards()) {
            return;
        }

        
        Card p1Down = player1.drawTopCard();
        Card p2Down = player2.drawTopCard();
        if (p1Down != null) pot.add(p1Down);
        if (p2Down != null) pot.add(p2Down);

        
        Card p1Up = player1.drawTopCard();
        Card p2Up = player2.drawTopCard();
        if (p1Up != null) pot.add(p1Up);
        if (p2Up != null) pot.add(p2Up);

        if (p1Up == null || p2Up == null) {
            return;
        }

        System.out.println(player1.getName() + " war card: " + p1Up);
        System.out.println(player2.getName() + " war card: " + p2Up);

        int result = compareCards(p1Up, p2Up);

        if (result > 0) {
            System.out.println(player1.getName() + " wins the war.");
            collectCards(player1, pot);
        } else if (result < 0) {
            System.out.println(player2.getName() + " wins the war.");
            collectCards(player2, pot);
        } else {
            System.out.println("War again!");
            startWar(pot); 
        }
    }

    @Override
    public void declareWinner() {
        System.out.println("=== Game Over ===");
        int p1 = player1.cardCount();
        int p2 = player2.cardCount();

        System.out.println("Player 1 cards: " + p1);
        System.out.println("Player 2 cards: " + p2);

        if (p1 > p2) {
            System.out.println(player1.getName() + " wins the game!");
        } else if (p2 > p1) {
            System.out.println(player2.getName() + " wins the game!");
        } else {
            System.out.println("It's a tie.");
        }
    }
}

