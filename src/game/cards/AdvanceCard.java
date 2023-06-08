package game.cards;

import game.Card;
import game.Player;

public class AdvanceCard extends Card implements CardInterface {
    private int multiplier;
    private int target;

    public AdvanceCard(String description, String type, int multiplier, int target) {
        super(description, type);
        this.multiplier = multiplier;
        this.target = target;
    }

    @Override
    public void cardAction(Player player) {

    }
}
