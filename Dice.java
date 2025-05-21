import java.util.Random;

public class Dice {
    private Random random;
    private int die1;
    private int die2;

    public Dice() {
        this.random = new Random();
    }

    public void roll() {
        die1 = random.nextInt(6) + 1;
        die2 = random.nextInt(6) + 1;
    }

    public int getDie1() {
        return die1;
    }

    public int getDie2() {
        return die2;
    }

    public int getTotal() {
        return die1 + die2;
    }

    public boolean isDouble() {
        return die1 == die2;
    }
}