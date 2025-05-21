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
        System.out.println("Players:");
        for (Player p : game.getPlayers()) {
            System.out.println("- " + p.getName());
        }
        System.out.println("Let the game begin!");

        int turnCount = 0;
        while (!game.isGameOver()) {
            Player player = game.getCurrentPlayer();
            System.out.println("\n--- Turn " + (turnCount + 1) + " ---");
            System.out.println("Current player: " + player.getName());
            System.out.println("Money: $" + player.getMoney());
            System.out.println("Position: " + player.getPosition() + " (" + board.getSpace(player.getPosition()).getName() + ")");
            if (!player.getProperties().isEmpty()) {
                System.out.print("Properties owned: ");
                player.getProperties().forEach(p -> System.out.print(p.getName() + ", "));
                System.out.println();
            } else {
                System.out.println("No properties owned.");
            }
            System.out.print("Press Enter to roll the dice...");
            scanner.nextLine();

            game.playTurn(board);

            if (game.isGameOver()) {
                System.out.println("Game over!");
                System.out.println("Winner: " + game.getPlayers().get(0).getName());
                break;
            }
            turnCount++;
        }
        scanner.close();
    }
}