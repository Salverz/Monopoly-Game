package game;

public abstract class Card {
    private String description;
    private String type;

    public Card(String description, String type) {
        this.description = description;
        this.type = type;
    }
}
