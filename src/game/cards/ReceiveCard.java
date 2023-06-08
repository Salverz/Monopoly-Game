package game.cards;

import game.Card;
import game.Player;

public class ReceiveCard extends Card implements CardInterface {
    private int amount;

    public ReceiveCard(String description, String type, int amount) {
        super(description, type);
        this.amount = amount;
    }

    @Override
    public void cardAction(Player player) {

    }
}
