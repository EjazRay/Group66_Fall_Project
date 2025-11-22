package wargame;

public class WarGamePlayer extends Player {

    private GroupOfCards pile;

    public WarGamePlayer(String name) {
        super(name);
        this.pile = new GroupOfCards(0);   
    }

    public GroupOfCards getPile() {
        return pile;
    }

    public void addCard(Card c) {
        pile.addCard(c);
    }

    public Card drawTopCard() {
        return pile.removeTopCard();
    }

    public boolean hasCards() {
        return !pile.isEmpty();
    }

    public int cardCount() {
        return pile.size();
    }

    @Override
    public void play() {
        
    }
}

