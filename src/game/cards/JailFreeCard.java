package game.cards;

import game.Card;
import game.Player;

public class JailFreeCard extends Card implements CardInterface {
    public JailFreeCard(String description, String type) {
        super(description, type);
    }

    @Override
    public void cardAction(Player player) {
        player.setGetOutOfJailFreeCards(player.getGetOutOfJailFreeCards() + 1);
        System.out.println(player + " now has " + player.getGetOutOfJailFreeCards() + " get out of jail free cards");
    }
}
