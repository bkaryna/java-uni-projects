package project1;

public class InvalidFigure extends Exception {
    public InvalidFigure() {
        super("Failed to build a figure with provided data");
    }

    public InvalidFigure(String message) {
        super(message);
    }
}
