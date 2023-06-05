package game.cards;

import game.Card;

public class PayCard extends Card implements CardInterface {
    private int amount;

    public PayCard(String description, String type, int amount) {
        super(description, type);
        this.amount = amount;
    }

    @Override
    public void cardAction() {

    }
}
