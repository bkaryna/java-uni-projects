package project1;

import java.util.ArrayList;
import java.util.Collections;

public class Triangle extends Polygon implements Calculable {
    private Point A;
    private Point B;
    private Point C;

    ArrayList<Double> distances;

    Triangle(ArrayList<Double> numbers) {
        super(numbers);
        A = new Point(numbers.get(0), numbers.get(1));
        B = new Point(numbers.get(2), numbers.get(3));
        ;
        C = new Point(numbers.get(4), numbers.get(5));

        distances = new ArrayList<>();
        distances.add(Point.distanceBetween(A, B));
        distances.add(Point.distanceBetween(A, C));
        distances.add(Point.distanceBetween(B, C));
        Collections.sort(distances);
    }

    protected static void isTriangle(ArrayList<Double> array) throws InvalidFigure {
        // 0    1    2    3    4    5
        //x1   y1   x2   y2   x3   y3

        Double area = array.get(0) * (array.get(3) - array.get(5)) + array.get(2) * (array.get(5) - array.get(1) + array.get(4) * (array.get(1) - array.get(3)));

        if (area == 0)
            throw new InvalidFigure("Given values do not form a triangle.");
    }

    protected String type() {
        Double c = distances.get(2);
        Double a = distances.get(1);
        Double b = distances.get(0);

        Double eps = 0.000001d;

        StringBuilder builder = new StringBuilder();

        //all calculations are adjusted to epsilon due to precision issues
        //type based on sides
        builder.append("\tType based on sides: ");

        if (Math.abs(c - a) < eps && Math.abs(c - b) < eps)
            builder.append("equilateral");
        else if (Math.abs(c - a) < eps ^ Math.abs(c - b) < eps ^ Math.abs(a - b) < eps)
            builder.append("isosceles");
        else
            builder.append("scalene");

        //type based on angles
        builder.append("; type based on angles: ");

        if (Math.abs(c * c - (a * a + b * b)) < eps)
            builder.append("right");
        else if (c * c < a * a + b * b)
            builder.append("acute-angled");
        else
            builder.append("obtuse-angled");

        return builder.toString();
    }

    public String calculateArea() {
        Double c = distances.get(2);
        Double a = distances.get(1);
        Double b = distances.get(0);

        Double p = (a + b + c) / 2.0;
        Double area = Math.sqrt(p * (p - a) * (p - b) * (p - c));
        return "Area: " + area;
    }

    public String calculatePerimeter() {
        Double perimeter = distances.get(0) + distances.get(1) + distances.get(2);
        return "Perimeter: " + perimeter;
    }

    @Override
    public void showInformation() {
        super.showInformation();
        System.out.println(this.type());
    }
}
