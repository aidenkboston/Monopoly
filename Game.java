import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> players;
    private Dice dice;
    private int currentPlayerIndex;
    private boolean isGameOver;

    public Game(List<String> playerNames, int startingMoney, int boardSize) {
        this.players = new ArrayList<>();
        for (String name : playerNames) {
            players.add(new Player(name, startingMoney));
        }
        this.dice = new Dice();
        this.currentPlayerIndex = 0;
        this.isGameOver = false;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public void playTurn(Board board) {
        Player player = getCurrentPlayer();

        if (player.isInJail()) {
            System.out.println(player.getName() + " is in jail (turn " + (player.getJailTurns() + 1) + ").");
            dice.roll();
            System.out.println(player.getName() + " rolled " + dice.getDie1() + " and " + dice.getDie2());
            if (dice.isDouble()) {
                System.out.println(player.getName() + " rolled doubles and is released from jail!");
                player.releaseFromJail();
            } else {
                player.incrementJailTurns();
                if (player.getJailTurns() >= 3) {
                    System.out.println(player.getName() + " has served 3 turns and pays $50 to get out.");
                    player.deductMoney(50);
                    player.releaseFromJail();
                } else {
                    System.out.println(player.getName() + " did not roll doubles and remains in jail.");
                    nextTurn();
                    return;
                }
            }
        }

        boolean rolledDouble;
        int doublesCount = 0;
        do {
            dice.roll();
            int total = dice.getTotal();
            rolledDouble = dice.isDouble();
            System.out.println(player.getName() + " rolled " + dice.getDie1() + " and " + dice.getDie2() + " (total: " + total + ")");
            boolean passedGo = player.move(total, board.getTotalSpaces());
            if (passedGo) {
                player.addMoney(200);
                System.out.println(player.getName() + " collected $200 for passing GO!");
            }
            BoardElement space = board.getSpace(player.getPosition());
            System.out.println(player.getName() + " landed on " + space.getName());

            if (space instanceof NonProperty) {
                String name = space.getName();
                if (name.equalsIgnoreCase("Go to Jail")) {
                    player.sendToJail();
                    System.out.println(player.getName() + " is sent to Jail!");
                    nextTurn();
                    return;
                }
                System.out.println(((NonProperty) space).getDescription());
            } else if (space instanceof Property) {
                Property property = (Property) space;
                if (!property.isOwned()) {
                    // For now, auto-buy if player can afford it
                    if (player.getMoney() >= property.getPriceToBuy()) {
                        player.deductMoney(property.getPriceToBuy());
                        property.setOwner(player);
                        player.addProperty(property);
                        System.out.println(player.getName() + " bought " + property.getName() + " for $" + property.getPriceToBuy());
                    } else {
                        System.out.println(player.getName() + " cannot afford " + property.getName());
                    }
                } else if (property.getOwner() != player) {
                    int rent = property.getBaseRent(); // No houses/hotels yet
                    player.deductMoney(rent);
                    property.getOwner().addMoney(rent);
                    System.out.println(player.getName() + " paid $" + rent + " to " + property.getOwner().getName());
                } else {
                    System.out.println(player.getName() + " owns this property.");
                }
            }

            if (player.getMoney() <= 0) {
                System.out.println(player.getName() + " is bankrupt and out of the game!");
                // Release all properties
                for (Property property : new ArrayList<>(player.getProperties())) {
                    property.setOwner(null);
                    player.removeProperty(property);
                }
                players.remove(player);
                // Adjust currentPlayerIndex if needed
                if (currentPlayerIndex >= players.size()) {
                    currentPlayerIndex = 0;
                }
                if (players.size() == 1) {
                    System.out.println(players.get(0).getName() + " wins the game!");
                    isGameOver = true;
                }
                return; // Immediately stop this turn
            }

            if (rolledDouble) {
                doublesCount++;
                if (doublesCount == 3) {
                    System.out.println(player.getName() + " rolled doubles three times in a row and is sent to Jail!");
                    player.sendToJail();
                    nextTurn();
                    return;
                }
                System.out.println(player.getName() + " rolled doubles and gets to roll again!");
            }
        } while (rolledDouble && !player.isInJail());

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