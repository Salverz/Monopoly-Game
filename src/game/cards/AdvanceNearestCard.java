package game.cards;

import game.Card;
import game.Player;

public class AdvanceNearestCard extends Card implements CardInterface {
    private int multiplier;
    private int[] target;

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

        int min = 40;
        for (int i = 0; i < target.length; i++) {
            if (distances[i] <= min) {
                min = distances[i];
            }
        }
        player.move(min, true);
    }
}
