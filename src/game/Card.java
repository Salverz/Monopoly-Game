package game;

import game.cards.CardInterface;

public abstract class Card implements CardInterface {
    private String description;
    private String type;

    public Card(String description, String type) {
        this.description = description;
        this.type = type;
    }

    @Override
    public void cardAction(Player player) {
        System.out.println("I am a card");
    }

    @Override
    public String toString() { return description; }
}
