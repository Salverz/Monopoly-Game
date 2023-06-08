package game;

import game.spaces.CardSpace;
import game.spaces.CornerSpace;
import game.spaces.PropertySpace;
import game.spaces.TaxSpace;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private final String name;
    private int money;
    private int position;
    private ArrayList<PropertySpace> properties;
    private boolean inJail;
    private int jailTurnsRemaining;
    private int currentRoll;
    private int getOutOfJailFreeCards;

    public Player(String name) {
        this.name = name;
        money = 1500;
        this.properties = new ArrayList<>();
    }

    public void move(int roll, boolean passGo) {
        if (jailTurnsRemaining > 0) {
            System.out.println(name + " is in jail for " + jailTurnsRemaining + " more turns");
            return;
        }

        currentRoll = roll;
        int lastPosition = position;
        position = (position + roll) % 40;
        if (position < lastPosition && passGo) {
            System.out.println("$200 for passing go");
            receiveMoney(200);
        }
        System.out.println("landed on " + Board.getSpace(position).getName());
        handleSpaceAction(Board.getSpace(position));
    }

    public void moveTo(int spaceId) {
        move(Math.abs(spaceId - position), true);
    }

    private void handleSpaceAction(Space space) {
        // Is in jail

        // Lands on a tax
        if (space instanceof TaxSpace) {
            if (!spendMoney(((TaxSpace) space).getAmount())) {
                notEnoughMoney(((TaxSpace) space).getAmount());
                spendMoney(((TaxSpace) space).getAmount());
            }
            System.out.println("paid " + space.getName() + " of $" + ((TaxSpace) space).getAmount());
            return;
        }

        // Lands on go to jail
        if (space instanceof CornerSpace && ((CornerSpace) space).getType().equals("to_jail")) {
            setInJail(true);
            return;
        }

        // Lands on chance / community chest
        if (space instanceof CardSpace) {
            System.out.println("Choose the card that was drawn: ");
            if (((CardSpace) space).getType().equals("chance")) {
                for (int i = 0; i < Board.chanceCards.size(); i++) {
                    System.out.println("\t(" + (i + 1) + ") " + Board.chanceCards.get(i));
                }
            } else {
                for (int i = 0; i < Board.communityChestCards.size(); i++) {
                    System.out.println("\t(" + (i + 1) + ") " + Board.communityChestCards.get(i));
                }
            }

            Scanner kb = new Scanner(System.in);
            int card = kb.nextInt();

            if (((CardSpace) space).getType().equals("chance")) {
                Board.chanceCards.get(card - 1).cardAction(this);
                return;
            }
            Board.communityChestCards.get(card - 1).cardAction(this);
        }

        // Lands on an owned property
        if (space instanceof PropertySpace && ((PropertySpace) space).isOwned()) {
            System.out.println(space + " is owned by " + ((PropertySpace) space).getOwner());
            if (((PropertySpace) space).getOwner() != this) {
                payRent((PropertySpace) space, 1);
            }
        }
    }

    public void notEnoughMoney(int moneyNeeded) {
        do {
            System.out.println("need $" + (moneyNeeded - money) + " more");
            Game.runCommand();
        } while (money < moneyNeeded);
    }

    public void purchaseProperty(PropertySpace property) {
        if (property.isOwned()) {
            System.out.println("property is already owned");
            return;
        }

        if (!spendMoney(property.getPrice())) {
            System.out.println("don't have enough funds (" + property.getPrice() + ")");
            return;
        }

        property.setOwner(this);
        properties.add(property);
        System.out.println(property.getName() + " purchased!");
        property.verifyRentLevel(true);
    }

    public void payRent(PropertySpace property, int multiplier) {
        if (property.getSetId() == 9) { // Utility
            multiplier = Math.max(multiplier, property.getRent());
        }

        if (!spendMoney(property.getRent() * multiplier)) {
            System.out.println(name + " does not have enough money to pay rent");
            notEnoughMoney(property.getRent() * multiplier);
            spendMoney(property.getRent() * multiplier);
        }
        System.out.println("$" + property.getRent() * multiplier + " paid to " + property.getOwner());
        property.getOwner().receiveMoney(property.getRent() * multiplier);
    }

    public ArrayList<PropertySpace> getPropertiesOwnedOfSet(int setId) {
        ArrayList<PropertySpace> setProperties = new ArrayList<>();
        for (PropertySpace propertySpace : properties) {
            if (setId == propertySpace.getSetId()) {
                setProperties.add(propertySpace);
            }
        }
        return setProperties;
    }

    public void mortgageProperty(String propertyName) {
        PropertySpace property = getOwnedPropertyByName(propertyName);

        if (property == null) {
            System.out.println(getName() + " does not own " + propertyName);
            return;
        }

        System.out.println("mortgaged " + property);
        receiveMoney(property.getPrice() / 2);
        property.setMortgaged(true);
        property.verifyRentLevel(false);
    }

    public void unmortgageProperty(String propertyName) {
        PropertySpace property = getOwnedPropertyByName(propertyName);

        if (property == null) {
            System.out.println(name + " does not own " + propertyName);
            return;
        }

        int unmortgagePrice = (int) (property.getPrice() / 2 + Math.round(property.getPrice() * .1));
        System.out.println("unmortgaged " + property + " for $" + unmortgagePrice);
        property.setMortgaged(false);
        spendMoney(unmortgagePrice);
        property.verifyRentLevel(true);
    }

    public PropertySpace getOwnedPropertyByName(String propertyName) {
        for (PropertySpace property : properties) {
            if (property.getName().equals(propertyName)) {
                return property;
            }
        }
        return null;
    }

    public boolean spendMoney(int amount) {
        if (amount > money) {
            return false;
        }
        money -= amount;
        return true;
    }

    public void receiveMoney(int amount) {
        money += amount;
    }

    public int getMoney() { return money; }

    public String getName() { return name; }

    public int getPosition() { return position; }

    public int getJailTurnsRemaining() { return jailTurnsRemaining; }

    public void setGetOutOfJailFreeCards(int getOutOfJailFreeCards) {
        this.getOutOfJailFreeCards = getOutOfJailFreeCards;
    }

    public void reduceJailTurnsRemaining() { jailTurnsRemaining--; }

    public boolean isInJail() { return inJail; }

    public int getGetOutOfJailFreeCards() { return getOutOfJailFreeCards; }

    public void setInJail(boolean inJail) {
        System.out.println(name + " is in jail");
        this.inJail = inJail;
        jailTurnsRemaining = inJail ? 3 : 0;
        position = 10; // jail
    }

    @Override
    public String toString() { return name; }
}
