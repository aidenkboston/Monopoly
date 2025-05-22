import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        Game game = new Game(
            Arrays.asList("Top Hat", "Battleship", "Racecar", "Scottie Dog"),
            1500,
            board.getTotalSpaces()
        );
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Monopoly!");
        System.out.println("Would you like to play fast or slow? (Enter 'fast' or 'slow')");
        String mode = scanner.nextLine().trim().toLowerCase();
        boolean slowMode = !mode.equals("fast");

        System.out.println("Players:");
        for (Player p : game.getPlayers()) {
            System.out.println("- " + p.getName());
        }
        System.out.println("Let the game begin!");

        int turnCount = 0;
        while (!game.isGameOver() && game.getPlayers().size() > 1) {
            System.out.println("\n--- Turn " + (turnCount + 1) + " ---");
            if (slowMode) {
                System.out.print("Press Enter to roll the dice...");
                scanner.nextLine();
            }
            game.playTurn(board);
            turnCount++;
        }

        System.out.println("Game over!");
        if (game.getPlayers().size() == 1) {
            System.out.println("Winner: " + game.getPlayers().get(0).getName());
        } else {
            System.out.println("No players left.");
        }
        scanner.close();
    }
}