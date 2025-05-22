import java.util.ArrayList;

public class Board {
    private ArrayList<BoardElement> spaces; // Updated to store BoardElement objects

    public Board() {
        // Initialize the board with standard Monopoly spaces
        spaces = new ArrayList<>();
        initializeSpaces();
    }

    private void initializeSpaces() {
        spaces.add(new NonProperty("GO", "Collect $200 as you pass."));
        spaces.add(new Property("Mediterranean Avenue", 60, 50, 2, 10, 30, 90, 160, 250));
        spaces.add(new NonProperty("Community Chest", "Draw a Community Chest card."));
        spaces.add(new Property("Baltic Avenue", 60, 50, 4, 20, 60, 180, 320, 450));
        spaces.add(new NonProperty("Income Tax", "Pay 10% or $200."));
        spaces.add(new NonProperty("Reading Railroad", "Pay rent if owned."));
        spaces.add(new Property("Oriental Avenue", 100, 50, 6, 30, 90, 270, 400, 550));
        spaces.add(new NonProperty("Chance", "Draw a Chance card."));
        spaces.add(new Property("Vermont Avenue", 100, 50, 6, 30, 90, 270, 400, 550));
        spaces.add(new Property("Connecticut Avenue", 120, 50, 8, 40, 100, 300, 450, 600));
        spaces.add(new NonProperty("Jail", "Just visiting or in jail."));
        spaces.add(new Property("St. Charles Place", 140, 100, 10, 50, 150, 450, 625, 750));
        spaces.add(new NonProperty("Electric Company", "Pay rent based on dice roll if owned."));
        spaces.add(new Property("States Avenue", 140, 100, 10, 50, 150, 450, 625, 750));
        spaces.add(new Property("Virginia Avenue", 160, 100, 12, 60, 180, 500, 700, 900));
        spaces.add(new Property("St. James Place", 180, 100, 14, 70, 200, 550, 750, 950));
        spaces.add(new Property("Tennessee Avenue", 180, 100, 14, 70, 200, 550, 750, 950));
        spaces.add(new Property("New York Avenue", 200, 100, 16, 80, 220, 600, 800, 1000));
        spaces.add(new NonProperty("Free Parking", "Take a break."));
        spaces.add(new Property("Kentucky Avenue", 220, 150, 18, 90, 250, 700, 875, 1050));
        spaces.add(new NonProperty("Chance", "Draw a Chance card."));
        spaces.add(new Property("Indiana Avenue", 220, 150, 18, 90, 250, 700, 875, 1050));
        spaces.add(new Property("Illinois Avenue", 240, 150, 20, 100, 300, 750, 925, 1100));
        spaces.add(new NonProperty("B&O Railroad", "Pay rent if owned."));
        spaces.add(new Property("Atlantic Avenue", 260, 150, 22, 110, 330, 800, 975, 1150));
        spaces.add(new Property("Ventnor Avenue", 260, 150, 22, 110, 330, 800, 975, 1150));
        spaces.add(new NonProperty("Water Works", "Pay rent based on dice roll if owned."));
        spaces.add(new Property("Marvin Gardens", 280, 150, 24, 120, 360, 850, 1025, 1200));
        spaces.add(new NonProperty("Go to Jail", "Go directly to jail. Do not pass GO. Do not collect $200."));
        spaces.add(new Property("Pacific Avenue", 300, 200, 26, 130, 390, 900, 1100, 1275));
        spaces.add(new Property("North Carolina Avenue", 300, 200, 26, 130, 390, 900, 1100, 1275));
        spaces.add(new NonProperty("Community Chest", "Draw a Community Chest card."));
        spaces.add(new Property("Pennsylvania Avenue", 320, 200, 28, 150, 450, 1000, 1200, 1400));
        spaces.add(new NonProperty("Short Line Railroad", "Pay rent if owned."));
        spaces.add(new NonProperty("Chance", "Draw a Chance card."));
        spaces.add(new Property("Park Place", 350, 200, 35, 175, 500, 1100, 1300, 1500));
        spaces.add(new NonProperty("Luxury Tax", "Pay $75."));
        spaces.add(new Property("Boardwalk", 400, 200, 50, 200, 600, 1400, 1700, 2000));
    }

    public BoardElement getSpace(int position) {
        if (position < 0 || position >= spaces.size()) {
            throw new IllegalArgumentException("Invalid board position");
        }
        return spaces.get(position);
    }

    public int getTotalSpaces() {
        return spaces.size();
    }
}