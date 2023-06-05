package game.cards;

import game.Card;

public class PayEachCard extends Card implements CardInterface {
    private int amount;

    public PayEachCard(String description, String type, int amount) {
        super(description, type);
        this.amount = amount;
    }

    @Override
    public void cardAction() {

    }
}
