package game;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import game.spaces.CardSpace;
import game.spaces.CornerSpace;
import game.spaces.PropertySpace;
import game.spaces.TaxSpace;

import java.io.FileReader;
import java.util.ArrayList;

public class Board {
    public static int houses = 32, hotels = 12;
    public static int communityChestCards = 16, chanceCards = 16;
    public static ArrayList<Space> spaces;

    public static Space getSpace(int position) {
        return spaces.get(position);
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
}
