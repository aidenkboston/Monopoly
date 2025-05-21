import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int position;
    private int money;
    private boolean inJail;
    private int jailTurns = 0; // Add this field
    private List<Property> properties; // List of owned properties

    public Player(String name, int startingMoney) {
        this.name = name;
        this.position = 0; // Start at "Go"
        this.money = startingMoney;
        this.inJail = false;
        this.properties = new ArrayList<>();
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

    public int getJailTurns() {
        return jailTurns;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public void removeProperty(Property property) {
        properties.remove(property);
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
        jailTurns = 0;
    }

    public void releaseFromJail() {
        inJail = false;
        jailTurns = 0;
    }

    public void incrementJailTurns() {
        jailTurns++;
    }
}