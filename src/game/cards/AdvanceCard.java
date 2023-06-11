package game.cards;

import game.Board;
import game.Card;
import game.Player;
import game.Space;
import game.spaces.PropertySpace;

public class AdvanceCard extends Card implements CardInterface {
    private final int target;

    public AdvanceCard(String description, String type, int target) {
        super(description, type);
        this.target = target;
    }

    @Override
    public void cardAction(Player player) {
        player.moveTo(target);

        if (target == 12 || target == 28) {  // Utility
            player.payRent((PropertySpace) Board.spaces.get(target), 10);
        }

        Space landedSpace = Board.spaces.get(target);
        if (landedSpace instanceof PropertySpace) {
            player.payRent((PropertySpace) landedSpace, 1);
        }
    }
}
