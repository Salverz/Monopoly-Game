package game.spaces;

import game.Space;

public class TaxSpace extends Space {
    private int amount;

    public TaxSpace(String name, int amount) {
        super(name);
        this.amount = amount;
    }

    public int getAmount() { return amount; }
}
