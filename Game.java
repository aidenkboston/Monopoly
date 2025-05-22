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

        System.out.println();
        System.out.println("==============================");
        System.out.println("It's " + player.getName() + "'s turn!");
        System.out.println("Current money: $" + player.getMoney());
        System.out.print("Properties owned: ");
        if (player.getProperties().isEmpty()) {
            System.out.println("None");
        } else {
            for (Property prop : player.getProperties()) {
                System.out.print(prop.getName());
                if (prop.hasHotel()) {
                    System.out.print(" (Hotel)");
                } else if (prop.getHouses() > 0) {
                    System.out.print(" (" + prop.getHouses() + " houses)");
                }
                System.out.print("; ");
            }
            System.out.println();
        }

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
            System.out.println();
            System.out.println(player.getName() + " rolled " + dice.getDie1() + " and " + dice.getDie2() + " (total: " + total + ")");
            boolean passedGo = player.move(total, board.getTotalSpaces());
            if (passedGo) {
                player.addMoney(200);
                System.out.println(player.getName() + " collected $200 for passing GO! New balance: $" + player.getMoney());
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
                } else if (name.equalsIgnoreCase("Income Tax")) {
                    int tax = Math.min(200, (int)(player.getMoney() * 0.10));
                    player.deductMoney(tax);
                    System.out.println(player.getName() + " pays Income Tax: $" + tax);
                } else if (name.equalsIgnoreCase("Luxury Tax")) {
                    player.deductMoney(75);
                    System.out.println(player.getName() + " pays Luxury Tax: $75");
                } else if (name.equalsIgnoreCase("Community Chest")) {
                    Card card = board.drawCommunityChest();
                    System.out.println("Community Chest: " + card.getDescription());
                    player.addMoney(card.getMoneyEffect());
                    System.out.println(player.getName() + " now has $" + player.getMoney());
                } else if (name.equalsIgnoreCase("Chance")) {
                    Card card = board.drawChance();
                    System.out.println("Chance: " + card.getDescription());
                    player.addMoney(card.getMoneyEffect());
                    System.out.println(player.getName() + " now has $" + player.getMoney());
                } else {
                    System.out.println("Space description: " + ((NonProperty) space).getDescription());
                }
            } else if (space instanceof Property) {
                Property property = (Property) space;
                System.out.println("Property status: " + (property.isOwned() ? "Owned by " + property.getOwner().getName() : "Unowned"));
                if (!property.isOwned()) {
                    int price = property.getPriceToBuy();
                    if (player.getMoney() - price > 0) { // Must have at least $1 left after purchase
                        System.out.println(player.getName() + " buys " + property.getName() + " for $" + price);
                        player.deductMoney(price);
                        property.setOwner(player);
                        player.addProperty(property);
                        System.out.println(player.getName() + " now has $" + player.getMoney());
                    } else {
                        System.out.println(player.getName() + " cannot afford " + property.getName() + " ($" + price + ") without going bankrupt.");
                    }
                } else if (property.getOwner() == player) {
                    int housePrice = property.getPricePerHouse();
                    if (property.canBuyHouse() && player.getMoney() - housePrice > 0
                        && player.ownsFullSet(property.getColor(), board.getSpaces())) {
                        property.buyHouse();
                        player.deductMoney(housePrice);
                        System.out.println(player.getName() + " bought a house on " + property.getName() + " for $" + housePrice);
                        System.out.println(player.getName() + " now has $" + player.getMoney());
                    } else if (property.canBuyHotel() && player.getMoney() - housePrice > 0
                        && player.ownsFullSet(property.getColor(), board.getSpaces())) {
                        property.buyHotel();
                        player.deductMoney(housePrice);
                        System.out.println(player.getName() + " bought a hotel on " + property.getName() + " for $" + housePrice);
                        System.out.println(player.getName() + " now has $" + player.getMoney());
                    }
                } else if (property.getOwner() != player) {
                    int rent = property.getRent(property.getHouses(), property.hasHotel());
                    System.out.println(player.getName() + " must pay rent: $" + rent + " to " + property.getOwner().getName());
                    player.deductMoney(rent);
                    property.getOwner().addMoney(rent);
                    System.out.println(player.getName() + " now has $" + player.getMoney());
                    System.out.println(property.getOwner().getName() + " now has $" + property.getOwner().getMoney());
                } else {
                    System.out.println(player.getName() + " owns this property.");
                }
            } else if (space instanceof Railroad) {
                Railroad rr = (Railroad) space;
                System.out.println("Railroad status: " + (rr.isOwned() ? "Owned by " + rr.getOwner().getName() : "Unowned"));
                if (!rr.isOwned()) {
                    if (player.getMoney() - rr.getPrice() > 0) {
                        System.out.println(player.getName() + " buys " + rr.getName() + " for $" + rr.getPrice());
                        player.deductMoney(rr.getPrice());
                        rr.setOwner(player);
                        player.addRailroad(rr); // You may want to add this method to Player
                        System.out.println(player.getName() + " now has $" + player.getMoney());
                    } else {
                        System.out.println(player.getName() + " cannot afford " + rr.getName() + " ($" + rr.getPrice() + ")");
                    }
                } else if (rr.getOwner() != player) {
                    int rent = rr.calculateRent(rr.getOwner(), board.getSpaces());
                    System.out.println(player.getName() + " must pay railroad rent: $" + rent + " to " + rr.getOwner().getName());
                    player.deductMoney(rent);
                    rr.getOwner().addMoney(rent);
                    System.out.println(player.getName() + " now has $" + player.getMoney());
                    System.out.println(rr.getOwner().getName() + " now has $" + rr.getOwner().getMoney());
                } else {
                    System.out.println(player.getName() + " owns this railroad.");
                }
            } else if (space instanceof Utility) {
                Utility util = (Utility) space;
                System.out.println("Utility status: " + (util.isOwned() ? "Owned by " + util.getOwner().getName() : "Unowned"));
                if (!util.isOwned()) {
                    if (player.getMoney() - util.getPrice() > 0) {
                        System.out.println(player.getName() + " buys " + util.getName() + " for $" + util.getPrice());
                        player.deductMoney(util.getPrice());
                        util.setOwner(player);
                        player.addUtility(util); // You may want to add this method to Player
                        System.out.println(player.getName() + " now has $" + player.getMoney());
                    } else {
                        System.out.println(player.getName() + " cannot afford " + util.getName() + " ($" + util.getPrice() + ")");
                    }
                } else if (util.getOwner() != player) {
                    int diceRoll = dice.getTotal();
                    int rent = util.calculateRent(util.getOwner(), board.getSpaces(), diceRoll);
                    System.out.println(player.getName() + " must pay utility rent: $" + rent + " to " + util.getOwner().getName());
                    player.deductMoney(rent);
                    util.getOwner().addMoney(rent);
                    System.out.println(player.getName() + " now has $" + player.getMoney());
                    System.out.println(util.getOwner().getName() + " now has $" + util.getOwner().getMoney());
                } else {
                    System.out.println(player.getName() + " owns this utility.");
                }
            }

            if (player.getMoney() <= 0) {
                System.out.println(player.getName() + " is bankrupt and out of the game!");
                for (Property property : new ArrayList<>(player.getProperties())) {
                    property.setOwner(null);
                    player.removeProperty(property);
                }
                players.remove(player);
                if (currentPlayerIndex >= players.size()) {
                    currentPlayerIndex = 0;
                }
                if (players.size() == 1) {
                    System.out.println(players.get(0).getName() + " wins the game!");
                    isGameOver = true;
                }
                return;
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

        System.out.println("End of " + player.getName() + "'s turn. Money: $" + player.getMoney());
        System.out.println("==============================");
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