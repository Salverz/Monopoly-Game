package game.cards;

import game.Card;
import game.Player;
import game.spaces.PropertySpace;

public class RepairsCard extends Card implements CardInterface {
    final int[] amounts;

    public RepairsCard(String description, String type, int[] amounts) {
        super(description, type);
        this.amounts = amounts.clone();
    }

    @Override
    public void cardAction(Player player) {
        int houses = 0, hotels = 0;
        for (PropertySpace property : player.getProperties()) {
            if (property.getSetId() == 8 || property.getSetId() == 9) {
                continue;
            }
            if (property.getRentLevel() < 2) {
                continue;
            }
            if (property.getRentLevel() == 6) {
                hotels++;
                continue;
            }
            houses += property.getRentLevel() - 1;
        }

        int totalOwed = houses * amounts[0] + hotels * amounts[1];
        if (!player.spendMoney(totalOwed)) {
            System.out.println(player + " does not have enough money");
            player.notEnoughMoney(totalOwed);
        }
        System.out.println(player + " paid $" + totalOwed + " for " + houses + " houses and " + hotels + " hotels");
    }
}
