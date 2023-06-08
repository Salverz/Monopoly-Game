package game;

import game.cards.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import game.spaces.CardSpace;
import game.spaces.CornerSpace;
import game.spaces.PropertySpace;
import game.spaces.TaxSpace;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    public static int houses = 32, hotels = 12;
    public static ArrayList<Card> communityChestCards, chanceCards;
    public static ArrayList<Space> spaces;

    public static Space getSpace(int position) {
        return spaces.get(position);
    }

    public static ArrayList<Space> spaceSearch(String spaceName) {
        spaceName = spaceName.toLowerCase();
        String[] words = spaceName.split("\\s+");
        ArrayList<Space> results = new ArrayList<>();

        for (int i = 0; i < spaces.size(); i++) {
            int matchingWords = 0;
            for (int j = 0; j < words.length; j++) {
                String space = spaces.get(i).toString().toLowerCase();
                String[] spaceWords = space.split("\\s+");
                if (j >= spaceWords.length) {
                    break;
                }
                int smallerWord = Math.min(words[j].length(), spaceWords[j].length());
                boolean wordMatches = true;
                for (int k = 0; k < smallerWord; k++) {
                    if (spaceWords[j].charAt(k) != words[j].charAt(k)) {
                        wordMatches = false;
                        break;
                    }
                }
                if (wordMatches) {
                    matchingWords++;
                }
            }
            if (matchingWords == words.length) {
                results.add(spaces.get(i));
            }
        }
        if (results.size() == 1) {
            return results;
        }
        return null;
    }

    public static void createSpaces() throws Exception {
        spaces = new ArrayList<>();
        JSONArray spacesJson = (JSONArray) new JSONParser().parse(new FileReader("C:\\Users\\David\\IdeaProjects\\MonopolyAI\\src\\game\\spaces.json"));

        for (int i = 0; i < 40; i++) {
            JSONObject space = (JSONObject) spacesJson.get(i);
            String name = (String) space.get("name");
            String type = (String) space.get("type");
            switch (type) {
                case "property":
                    int price = (int) (long) space.get("price");
                    int setId = (int) (long) space.get("set_id");
                    JSONArray rents = (JSONArray) space.get("rents");
                    int[] intRents = new int[rents.size()];
                    for (int j = 0; j < rents.size(); j++) {
                        intRents[j] = (int) (long) rents.get(j);
                    }
                    spaces.add(new PropertySpace(name, price, intRents, setId));
                    break;
                case "chance", "community_chest":
                    spaces.add(new CardSpace(name, type));
                    break;
                case "tax":
                    int amount = (int) (long) space.get("amount");
                    spaces.add(new TaxSpace(name, amount));
                    break;
                default:
                    spaces.add(new CornerSpace(name, type));
            }
        }
    }

    public static void createCards() throws Exception {
        chanceCards = new ArrayList<>();
        communityChestCards = new ArrayList<>();

        JSONArray cardsJson = (JSONArray) new JSONParser().parse(new FileReader("C:\\Users\\David\\IdeaProjects\\MonopolyAI\\src\\game\\cards.json"));
        for (int i = 0; i < 32; i++) {
            Card card = null;
            JSONObject currentCard = (JSONObject) cardsJson.get(i);
            String description = (String) currentCard.get("description");
            String type = (String) currentCard.get("type");
            int multiplier = 0, target = 0, amount = 0;
            String nearestTarget = "";
            if (type.equals("advance")) {
                multiplier = (int) (long) currentCard.get("multiplier");
                target = (int) (long) currentCard.get("target");
            }
            if (type.equals("advance_nearest")) {
                nearestTarget = (String) currentCard.get("target");
            }

            if (type.equals("pay") || type.equals("pay_each") || type.equals("receive") || type.equals("receive_each")) {
                amount = (int) (long) currentCard.get("amount");
            }

            switch (type) {
                case "advance":
                    card = new AdvanceCard(description, type, multiplier, target);
                    break;
                case "advance_nearest":
                    card = new AdvanceNearestCard(description, type, multiplier, nearestTarget);
                    break;
                case "jail":
                    card = new JailCard(description, type);
                    break;
                case "jail_free":
                    card = new JailFreeCard(description, type);
                    break;
                case "pay":
                    card = new PayCard(description, type, amount);
                    break;
                case "pay_each":
                    card = new PayEachCard(description, type, amount);
                    break;
                case "receive":
                    card = new ReceiveCard(description, type, amount);
                    break;
                case "receive_each":
                    card = new ReceiveEachCard(description, type, amount);
                    break;
                case "repairs":
                    JSONArray amounts = (JSONArray) currentCard.get("amount");
                    int[] amountsArray = new int[amounts.size()];
                    for (int j = 0; j < amounts.size(); j++) {
                        amountsArray[j] = (int) (long) amounts.get(j);
                    }
                    card = new RepairsCard(description, type, amountsArray);
                    break;
            }

            if (i < 16) {
                chanceCards.add(card);
                continue;
            }
            communityChestCards.add(card);
        }
    }
}
