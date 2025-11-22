package wargame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupOfCards {

    private int size;
    private List<Card> cards;

    public GroupOfCards(int size) {
        this.size = size;
        this.cards = new ArrayList<>();
    }

    public List<Card> getCards() {
        return cards;
    }

    public int size() {
        return cards.size();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public void addCard(Card c) {
        cards.add(c);
    }

    public Card removeTopCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(0);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }
}
