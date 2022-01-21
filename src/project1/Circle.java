package project1;

import java.util.ArrayList;

public class Circle extends Figure implements Calculable {
    Double radius;

    Circle(ArrayList<Double> numbers) {
        super(numbers.get(0), numbers.get(1));
        this.radius = numbers.get(2);
    }

    public void setRadius(Double r) {
        this.radius = r;
    }

    public String calculateArea() {
        Double area = Math.PI*this.radius*this.radius;
        return "Area: " + area;
    }

    public String calculatePerimeter() {
        Double perimeter = 2*Math.PI*this.radius;
        return "Perimeter: " + perimeter;
    }
}
