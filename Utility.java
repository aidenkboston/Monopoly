import java.util.List;

public class Utility extends BoardElement {
    private Player owner;
    private int price;

    public Utility(String name, int price) {
        super(name, "Utility");
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public boolean isOwned() {
        return owner != null;
    }

    // Rent: 4x dice roll if one utility, 10x if both
    public int calculateRent(Player owner, List<BoardElement> spaces, int diceRoll) {
        int count = 0;
        for (BoardElement elem : spaces) {
            if (elem instanceof Utility && ((Utility) elem).getOwner() == owner) {
                count++;
            }
        }
        return diceRoll * (count == 2 ? 10 : 4);
    }
}