package game;

public class CommandHandler {
    public static void handleCommand(String command, Player player) {
        String[] args = command.split("\\s+");
        switch (args[0]) {
            case "next":
                Actions.nextTurn(player);
                break;

            case "money":
                if (args.length == 1) {
                    Actions.getPlayerMoney(player);
                    break;
                }

                if (args[1].equals("add")) {
                    Actions.addPlayerMoney(player, Integer.parseInt(args[2]));
                    break;
                }

                if (args[1].equals("remove")) {
                    Actions.removePlayerMoney(player, Integer.parseInt(args[2]));
                    break;
                }

            case "move":
                Actions.move(player, Integer.parseInt(args[1]));
                break;

            case "buy":
                Actions.buy(player);
                break;

            case "mortgage":
                player.mortgageProperty(deleteFirstWord(args));
                break;

            case "unmortgage":
                player.unmortgageProperty(deleteFirstWord(args));
                break;

            case "jail":
                boolean pay = false;
                if (args.length > 1 && args[1].equals("exit")) {
                    if (args.length > 2 && args[2].equals("pay")) {
                        pay = true;
                    }
                    Actions.jailExit(player, pay);
                    break;
                }
                Actions.jail(player);
                break;

            case "house":
                Actions.house(player, deleteFirstWord(args));
                break;

            case "trade":
                Player tradingPartner = Game.getPlayerByName(args[1]);
                if (tradingPartner == null || tradingPartner == player) {
                    System.out.println("invalid trading partner");
                    break;
                }

                Actions.trade(new Player[]{player, tradingPartner});
                break;

            default:
                System.out.println("invalid command");
                break;
        }
    }

    private static String deleteFirstWord(String[] wordArray) {
        if (wordArray.length <= 2) {
            return wordArray[wordArray.length - 1];
        }

        String newWord = wordArray[1];
        for (int i = 2; i < wordArray.length; i++) {
            newWord = newWord.concat(" " + wordArray[i]);
        }
        return newWord;
    }
}
