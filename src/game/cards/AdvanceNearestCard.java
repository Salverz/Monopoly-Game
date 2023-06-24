package game.cards;

import game.Board;
import game.Card;
import game.Player;
import game.Space;
import game.spaces.PropertySpace;

public class AdvanceNearestCard extends Card implements CardInterface {
    private final int multiplier;
    private final int[] target;

    public AdvanceNearestCard(String description, String type, int multiplier, int[] target) {
        super(description, type);
        this.multiplier = multiplier;
        this.target = target.clone();
    }

    @Override
    public void cardAction(Player player) {
        int[] distances = new int[target.length];
        for (int i = 0; i < target.length; i++) {
            distances[i] = target[i] - player.getPosition();
            if (distances[i] < 0) {
                distances[i] += 40;
            }
        }

        int min = 40, minIndex = 0;
        for (int i = 0; i < target.length; i++) {
            if (distances[i] <= min) {
                min = distances[i];
                minIndex = i;
            }
        }
        player.moveTo(target[minIndex]);
        Space landedSpace = Board.spaces.get(target[minIndex]);
        if (landedSpace instanceof PropertySpace landedProperty) {
            if (landedProperty.getOwner() == null) {
                return;
            }
            System.out.println(landedProperty + " is owned by " + landedProperty.getOwner());
            if (landedProperty.getOwner() == player) {
                return;
            }
            if (landedProperty.getSetId() == 9) { // Utility
                if (landedProperty.getRentLevel() == 0) {
                    player.payRent(landedProperty, player.getCurrentRoll());
                    return;
                }
                player.payRent(landedProperty, player.getCurrentRoll() * 2.5);
                return;
            }
            player.payRent(landedProperty, multiplier);
            return;
        }
        player.handleSpaceAction(landedSpace);
    }
}
