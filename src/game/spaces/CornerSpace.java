package game.spaces;

import game.Space;

public class CornerSpace extends Space {
    private String type;

    public CornerSpace(String name, String type) {
        super(name);
        this.type = type;
    }

    public String getType() { return type; }
}
