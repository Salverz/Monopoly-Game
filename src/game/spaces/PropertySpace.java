package game.spaces;

import game.Player;
import game.Space;

import java.util.ArrayList;
import java.util.Arrays;

public class PropertySpace extends Space {
    private Player owner;
    private final int price;
    private final int[] rents;
    private final int setId;
    private int rentLevel;
    private boolean isMortgaged;

    public PropertySpace(String name, int price, int[] rents, int setId) {
        super(name);
        this.price = price;
        this.rents = rents.clone();
        this.setId = setId;
    }

    public void verifyRentLevel(boolean gainingProperty) {
        int setId = getSetId();
        ArrayList<PropertySpace> setProperties = getOwner().getPropertiesOwnedOfSet(setId);
        int mortgagedProperties = 0;
        for (PropertySpace propertySpace : setProperties) {
            if (propertySpace.isMortgaged()) {
                mortgagedProperties++;
            }
        }
        int unmortgagedProperties = setProperties.size() - mortgagedProperties;

        System.out.println("list of properties from this set " + setProperties);

        int newRentLevel;
        if (Arrays.asList(0, 7, 8, 9).contains(setId) && unmortgagedProperties < 2 ||
                setId < 7 && setId > 0 && unmortgagedProperties < 3) { // Player has fewer than needed for a set
            newRentLevel = 0;
        } else {
            int currentRentLevel = setProperties.get(0).getRentLevel();
            newRentLevel = gainingProperty ? currentRentLevel + 1 : currentRentLevel - 1;
        }

        for (PropertySpace propertySpace : setProperties) {
            propertySpace.setRentLevel(newRentLevel);
        }
    }

    public Player getOwner() { return owner; }

    public int getRentLevel() { return rentLevel; }

    public void setRentLevel(int rentLevel) { this.rentLevel = rentLevel; }

    public int getRent() {
        if (isMortgaged) {
            System.out.println(getName() + " is mortgaged");
            return 0;
        }

        return rents[rentLevel];
    }

    public void setMortgaged(boolean mortgaged) { isMortgaged = mortgaged; }

    public boolean isMortgaged() { return isMortgaged; }

    public int getSetId() { return setId; }

    public boolean isOwned() { return owner != null; }

    public int getPrice() { return price; }

    public void setOwner(Player player) { owner = player; }
}
