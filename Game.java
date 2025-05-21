import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> players;
    private Dice dice;
    private int boardSize;
    private int currentPlayerIndex;
    private boolean isGameOver;

    public Game(List<String> playerNames, int startingMoney, int boardSize) {
        this.players = new ArrayList<>();
        for (String name : playerNames) {
            players.add(new Player(name, startingMoney));
        }
        this.dice = new Dice();
        this.boardSize = boardSize;
        this.currentPlayerIndex = 0;
        this.isGameOver = false;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public void playTurn() {
        Player player = getCurrentPlayer();
        if (player.isInJail()) {
            System.out.println(player.getName() + " is in jail and misses this turn.");
            nextTurn();
            return;
        }
        dice.roll();
        int total = dice.getTotal();
        System.out.println(player.getName() + " rolled " + dice.getDie1() + " and " + dice.getDie2() + " (total: " + total + ")");
        player.move(total, boardSize);
        System.out.println(player.getName() + " moved to position " + player.getPosition());
        // Add more game logic here (property, rent, etc.)
        nextTurn();
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void endGame() {
        isGameOver = true;
    }

    public List<Player> getPlayers() {
        return players;
    }
}