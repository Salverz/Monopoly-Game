package game.spaces;

import game.Space;

public class CardSpace extends Space {
    private String type;

    public CardSpace(String name, String type) {
        super(name);
        this.type = type;
    }

    public String getType() { return type; }
}
