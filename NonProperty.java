public class NonProperty extends BoardElement {
    private String description;

    public NonProperty(String name, String description) {
        super(name);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "NonProperty: " + getName() + ", Description: " + description;
    }
}