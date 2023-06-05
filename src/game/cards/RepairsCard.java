package game.cards;

import game.Card;

public class RepairsCard extends Card implements CardInterface {
    int[] amounts;

    public RepairsCard(String description, String type, int[] amounts) {
        super(description, type);
        this.amounts = amounts.clone();
    }

    @Override
    public void cardAction() {

    }
}
