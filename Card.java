public class Card {
    private String description;
    private int moneyEffect; // Positive for gain, negative for loss

    public Card(String description, int moneyEffect) {
        this.description = description;
        this.moneyEffect = moneyEffect;
    }

    public String getDescription() {
        return description;
    }

    public int getMoneyEffect() {
        return moneyEffect;
    }
}