package game.cards;

import game.Card;
import game.Player;

public class JailCard extends Card implements CardInterface {
    public JailCard(String description, String type) {
        super(description, type);
    }

    @Override
    public void cardAction(Player player) {
        System.out.println(player + " is in jail");
        player.setInJail(true);
    }
}
