public abstract class BoardElement {
    private String name;
    private String color; // Add color field

    public BoardElement(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "BoardElement: " + name + (color != null ? " (" + color + ")" : "");
    }
}