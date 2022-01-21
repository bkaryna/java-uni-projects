package project1;

import java.util.ArrayList;

public class Rectangle extends Parallelogram implements Calculable {
    Rectangle(ArrayList<Double> numbers) {
        super(numbers);
    }

    public String calculateArea() {
        return "Area: " + (this.getdAB() * this.getdAD());
    }
}
