package game.cards;

import game.Card;
import game.Game;
import game.Player;

public class ReceiveEachCard extends Card implements CardInterface {
    private final int amount;

    public ReceiveEachCard(String description, String type, int amount) {
        super(description, type);
        this.amount = amount;
    }

    @Override
    public void cardAction(Player player) {
        for (Player opponent : Game.players) {
            if (opponent == player) {
                continue;
            }
            if (!opponent.spendMoney(amount)) {
                System.out.println(opponent + " does not have enough money");
                opponent.notEnoughMoney(amount);
            }
            System.out.println(opponent + " paid $" + amount);
        }
        player.receiveMoney(amount * (Game.players.size() - 1));
    }
}
