import java.util.List;

public class Railroad extends BoardElement {
    private Player owner;
    private int price;
    private int baseRent;

    public Railroad(String name, int price, int baseRent) {
        super(name, "Railroad");
        this.price = price;
        this.baseRent = baseRent;
    }

    public int getPrice() {
        return price;
    }

    public int getBaseRent() {
        return baseRent;
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

    // Rent is 25 * 2^(number of railroads owned - 1)
    public int calculateRent(Player owner, List<BoardElement> spaces) {
        int count = 0;
        for (BoardElement elem : spaces) {
            if (elem instanceof Railroad && ((Railroad) elem).getOwner() == owner) {
                count++;
            }
        }
        if (count == 0) count = 1; // Defensive: at least one railroad
        return baseRent * (int)Math.pow(2, count - 1);
    }

    public void calculateAndPrintRent(Board board) {
        int rent = calculateRent(getOwner(), board.getSpaces());
        System.out.println("Calculated Rent: " + rent);
    }
}