package game;

public abstract class Space {
    private final String name;

    public Space(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() { return name; }
}
