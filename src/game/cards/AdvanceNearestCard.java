package game.cards;

import game.Card;
import game.Player;

public class AdvanceNearestCard extends Card implements CardInterface {
    private int multiplier;
    private String target;

    public AdvanceNearestCard(String description, String type, int multiplier, String target) {
        super(description, type);
        this.multiplier = multiplier;
        this.target = target;
    }

    @Override
    public void cardAction(Player player) {

    }
}
