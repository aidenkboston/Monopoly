import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        Game game = setupGame(board);
        Scanner scanner = new Scanner(System.in);
        boolean slowMode = chooseGameSpeed(scanner);
        printPlayers(game);
        runGameLoop(game, board, scanner, slowMode);
        printGameResult(game);
        scanner.close();
    }

    private static Game setupGame(Board board) {
        return new Game(
            Arrays.asList("Top Hat", "Battleship", "Racecar", "Scottie Dog"),
            1500,
            board.getTotalSpaces()
        );
    }

    private static boolean chooseGameSpeed(Scanner scanner) {
        System.out.println("Welcome to Monopoly!");
        System.out.println("Would you like to play fast or slow? (Enter 'fast' or 'slow')");
        String mode = scanner.nextLine().trim().toLowerCase();
        return !mode.equals("fast");
    }

    private static void printPlayers(Game game) {
        System.out.println("Players:");
        for (Player p : game.getPlayers()) {
            System.out.println("- " + p.getName());
        }
        System.out.println("Let the game begin!");
    }

    private static void runGameLoop(Game game, Board board, Scanner scanner, boolean slowMode) {
        int turnCount = 0;
        int maxTurns = 10000;
        while (!game.isGameOver() && game.getPlayers().size() > 1 && turnCount < maxTurns) {
            System.out.println("\n--- Turn " + (turnCount + 1) + " ---");
            if (slowMode) {
                System.out.print("Press Enter to roll the dice...");
                scanner.nextLine();
            }
            game.playTurn(board);
            turnCount++;
        }
        if (turnCount >= maxTurns) {
            System.out.println("\nTurn limit reached (" + maxTurns + " turns).");
            Player richest = null;
            int maxMoney = Integer.MIN_VALUE;
            for (Player p : game.getPlayers()) {
                if (p.getMoney() > maxMoney) {
                    maxMoney = p.getMoney();
                    richest = p;
                }
            }
            if (richest != null) {
                System.out.println("Winner by wealth: " + richest.getName() + " with $" + richest.getMoney());
            } else {
                System.out.println("No winner could be determined.");
            }
            game.endGame();
        }
    }

    private static void printGameResult(Game game) {
        System.out.println("Game over!");
        if (game.getPlayers().size() == 1) {
            System.out.println("Winner: " + game.getPlayers().get(0).getName());
        } else {
            System.out.println("No players left.");
        }
    }
}