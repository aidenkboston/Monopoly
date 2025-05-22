public class Property extends BoardElement {
    private int priceToBuy;
    private int pricePerHouse;
    private int baseRent;
    private int rentWithOneHouse;
    private int rentWithTwoHouses;
    private int rentWithThreeHouses;
    private int rentWithFourHouses;
    private int hotelRent;
    private Player owner = null;
    private int houses = 0;
    private boolean hotel = false;

    public Property(String name, String color, int priceToBuy, int pricePerHouse, int baseRent, int rentWithOneHouse,
                      int rentWithTwoHouses, int rentWithThreeHouses, int rentWithFourHouses, int hotelRent) {
        super(name, color); // Pass color to BoardElement
        this.priceToBuy = priceToBuy;
        this.pricePerHouse = pricePerHouse;
        this.baseRent = baseRent;
        this.rentWithOneHouse = rentWithOneHouse;
        this.rentWithTwoHouses = rentWithTwoHouses;
        this.rentWithThreeHouses = rentWithThreeHouses;
        this.rentWithFourHouses = rentWithFourHouses;
        this.hotelRent = hotelRent;
    }

    public int getRent(int houses, boolean hasHotel) {
        if (hasHotel) {
            return hotelRent;
        }
        switch (houses) {
            case 1:
                return rentWithOneHouse;
            case 2:
                return rentWithTwoHouses;
            case 3:
                return rentWithThreeHouses;
            case 4:
                return rentWithFourHouses;
            default:
                return baseRent;
        }
    }

    public int getPriceToBuy() {
        return priceToBuy;
    }

    public int getPricePerHouse() {
        return pricePerHouse;
    }

    public int getBaseRent() {
        return baseRent;
    }

    public int getHotelRent() {
        return hotelRent;
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

    public int getHouses() {
        return houses;
    }

    public boolean hasHotel() {
        return hotel;
    }

    public boolean canBuyHouse() {
        return !hotel && houses < 4;
    }

    public boolean canBuyHotel() {
        return !hotel && houses == 4;
    }

    public void buyHouse() {
        if (canBuyHouse()) {
            houses++;
        }
    }

    public void buyHotel() {
        if (canBuyHotel()) {
            hotel = true;
            houses = 0;
        }
    }

    @Override
    public String toString() {
        return "Property: " + getName() + ", Color: " + getColor() + ", Price: $" + priceToBuy + ", Base Rent: $" + baseRent +
               ", Rent with 1 House: $" + rentWithOneHouse + ", Rent with 2 Houses: $" + rentWithTwoHouses +
               ", Rent with 3 Houses: $" + rentWithThreeHouses + ", Rent with 4 Houses: $" + rentWithFourHouses +
               ", Hotel Rent: $" + hotelRent;
    }
}