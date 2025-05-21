public class Player {
    private String name;
    private int position;
    private int money;
    private boolean inJail;

    public Player(String name, int startingMoney) {
        this.name = name;
        this.position = 0; // Start at "Go"
        this.money = startingMoney;
        this.inJail = false;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public int getMoney() {
        return money;
    }

    public boolean isInJail() {
        return inJail;
    }

    public void move(int steps, int boardSize) {
        position = (position + steps) % boardSize;
    }

    public void addMoney(int amount) {
        money += amount;
    }

    public void deductMoney(int amount) {
        money -= amount;
    }

    public void sendToJail() {
        inJail = true;
        position = 10; // Assuming 10 is Jail position
    }

    public void releaseFromJail() {
        inJail = false;
    }
}