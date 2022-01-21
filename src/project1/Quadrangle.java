package project1;

import java.util.ArrayList;

public class Quadrangle extends Polygon implements Calculable {
    private Point A;
    private Point B;
    private Point C;
    private Point D;

    private Double dAB;
    private Double dBC;
    private Double dCD;
    private Double dAD;

    Quadrangle(ArrayList<Double> numbers) {
        super(numbers);
        A = new Point(numbers.get(0), numbers.get(1));
        B = new Point(numbers.get(2), numbers.get(3));
        C = new Point(numbers.get(4), numbers.get(5));
        D = new Point(numbers.get(6), numbers.get(7));

        dAB = Point.distanceBetween(A, B);
        dBC = Point.distanceBetween(B, C);
        dCD = Point.distanceBetween(C, D);
        dAD = Point.distanceBetween(A, D);
    }

    public String determineType() {
        String type = "";

        if (Point.threePointsOnOneSlope(this.A, this.B, this.C) || Point.threePointsOnOneSlope(this.B, this.C, this.D) || Point.threePointsOnOneSlope(this.C, this.D, this.A) || Point.threePointsOnOneSlope(this.D, this.A, this.B)) {
            type = "error";
        } else {
            Double slopeAB = Point.slope(B, A);
            Double slopeCD = Point.slope(D, C);

            Double slopeAD = Point.slope(D, A);
            Double slopeBC = Point.slope(C, B);

            if (slopeAB.equals(slopeCD) ^ slopeAD.equals(slopeBC)) {
                type = "Trapezoid";
            }
            if (slopeAB.equals(slopeCD) && slopeAD.equals(slopeBC) && dAB.equals(dCD) && dAD.equals(dBC)) {
                type = "Parallelogram";
                //check diagonals
                Double dAC = Point.distanceBetween(A, C);
                Double dBD = Point.distanceBetween(B, D);

                if (dAC.equals(dBD)) {
                    type = "Rectangle";
                    if (dAB.equals(dAD))
                        type = "Square";
                } else {
                    if (dAB.equals(dAD))
                        type = "Rhombus";
                }
            }
        }

        if (!type.isEmpty())
            return type;
        else
            return "Quadrangle";
    }

    public String calculatePerimeter() {
        Double perimeter = this.dAB + this.dBC + this.dCD + this.dAD;
        return "Perimeter: " + perimeter;
    }

    public Double getdAB() {
        return dAB;
    }

    public Double getdAD() {
        return dAD;
    }
}
