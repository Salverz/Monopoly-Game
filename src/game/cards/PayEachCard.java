package game.cards;

import game.Card;
import game.Game;
import game.Player;

public class PayEachCard extends Card implements CardInterface {
    private final int amount;

    public PayEachCard(String description, String type, int amount) {
        super(description, type);
        this.amount = amount;
    }

    @Override
    public void cardAction(Player player) {
        if (!player.spendMoney(amount * (Game.players.size() - 1))) {
            System.out.println(player + " does not have enough money to pay each player");
            player.notEnoughMoney(amount * (Game.players.size() - 1));
        }

        for (Player opponent : Game.players) {
            if (opponent != player) {
                System.out.println("$" + amount + " paid to " + opponent);
                opponent.receiveMoney(amount);
            }
        }
    }
}
