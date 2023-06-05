package game;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    public static Player currentTurnPlayer;
    private static boolean nextPlayer;
    private static final Scanner kb = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        Board.createSpaces();
        ArrayList<Player> players = new ArrayList<>();

        System.out.print("Number of players: ");
        int numberOfPlayers = kb.nextInt();
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.print("Name of player " + (i + 1) + ": ");
            players.add(new Player(kb.next()));
        }
        kb.nextLine();

        while (true) {
            for (Player player : players) {
                currentTurnPlayer = player;
                nextPlayer = false;
                System.out.println(currentTurnPlayer + "'s turn:");
                System.out.println();
                while (!nextPlayer) {
                    runCommand();
                    System.out.println();
                }
            }
        }
    }

    public static void runCommand() {
        try {
            CommandHandler.handleCommand(kb.nextLine(), currentTurnPlayer);
        } catch (IndexOutOfBoundsException | NumberFormatException | InputMismatchException e) {
            System.out.println("invalid command");
        }
    }

    public static void nextPlayer() { nextPlayer = true; }
}
