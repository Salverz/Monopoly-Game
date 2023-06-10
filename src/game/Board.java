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

    public static Space spaceSearch(String spaceName) {
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
            return results.get(0);
        }
        return null;
    }

    public static void createSpaces() throws Exception {
        spaces = new ArrayList<>();
        JSONArray spacesJson = (JSONArray) new JSONParser().parse(new FileReader("C:\\Users\\david\\IdeaProjects\\MonopolyAI\\src\\game\\spaces.json"));

        for (int i = 0; i < 40; i++) {
            JSONObject space = (JSONObject) spacesJson.get(i);
            String name = (String) space.get("name");
            String type = (String) space.get("type");
            switch (type) {
                case "property" -> {
                    int price = (int) (long) space.get("price");
                    int setId = (int) (long) space.get("set_id");
                    JSONArray rents = (JSONArray) space.get("rents");
                    int[] intRents = new int[rents.size()];
                    for (int j = 0; j < rents.size(); j++) {
                        intRents[j] = (int) (long) rents.get(j);
                    }
                    spaces.add(new PropertySpace(name, price, intRents, setId));
                }
                case "chance", "community_chest" -> spaces.add(new CardSpace(name, type));
                case "tax" -> {
                    int amount = (int) (long) space.get("amount");
                    spaces.add(new TaxSpace(name, amount));
                }
                default -> spaces.add(new CornerSpace(name, type));
            }
        }
    }

    public static void createCards() throws Exception {
        chanceCards = new ArrayList<>();
        communityChestCards = new ArrayList<>();

        JSONArray cardsJson = (JSONArray) new JSONParser().parse(new FileReader("C:\\Users\\david\\IdeaProjects\\MonopolyAI\\src\\game\\cards.json"));
        for (int i = 0; i < 32; i++) {
            Card card = null;
            JSONObject currentCard = (JSONObject) cardsJson.get(i);
            String description = (String) currentCard.get("description");
            String type = (String) currentCard.get("type");
            int amount = 0;

            switch (type) {
                case "advance" -> {
                    int target = (int) (long) currentCard.get("target");
                    card = new AdvanceCard(description, type, target);
                }
                case "advance_nearest" -> {
                    int multiplier = (int) currentCard.get("multiplier");
                    JSONArray targets = (JSONArray) currentCard.get("target");
                    int[] intTargets = new int[targets.size()];
                    for (int j = 0; j < targets.size(); j++) {
                        intTargets[j] = (int) targets.get(j);
                    }
                    card = new AdvanceNearestCard(description, type, multiplier, intTargets);
                }
                case "jail" -> card = new JailCard(description, type);
                case "jail_free" -> card = new JailFreeCard(description, type);
                case "repairs" -> {
                    JSONArray amounts = (JSONArray) currentCard.get("amount");
                    int[] amountsArray = new int[amounts.size()];
                    for (int j = 0; j < amounts.size(); j++) {
                        amountsArray[j] = (int) (long) amounts.get(j);
                    }
                    card = new RepairsCard(description, type, amountsArray);
                }
                default -> amount = (int) (long) currentCard.get("amount");
            }

            card = switch (type) {
                case "pay" -> new PayCard(description, type, amount);
                case "pay_each" -> new PayEachCard(description, type, amount);
                case "receive" -> new ReceiveCard(description, type, amount);
                case "receive_each" -> new ReceiveEachCard(description, type, amount);
                default -> card;
            };





            if (type.equals("pay") || type.equals("pay_each") || type.equals("receive") || type.equals("receive_each")) {
                amount = (int) (long) currentCard.get("amount");
            }

            switch (type) {


            }

            if (i < 16) {
                chanceCards.add(card);
                continue;
            }
            communityChestCards.add(card);
        }
    }
}
