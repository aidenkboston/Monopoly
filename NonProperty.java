public class NonProperty extends BoardElement {
    private String description;

    public NonProperty(String name, String description) {
        super(name, null); // No color for non-properties by default
        this.description = description;
    }

    // Optionally, if you want to assign a color/type:
    public NonProperty(String name, String color, String description) {
        super(name, color);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "NonProperty: " + getName() + (getColor() != null ? " (" + getColor() + ")" : "") + ", Description: " + description;
    }
}