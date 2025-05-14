public abstract class BoardElement {
    private String name;

    public BoardElement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "BoardElement: " + name;
    }
}