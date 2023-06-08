package game;

public abstract class Space {
    private final String name;

    public Space(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        for (int i = 0; i < Board.spaces.size(); i++) {
            if (Board.spaces.get(i).toString().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() { return name; }
}
