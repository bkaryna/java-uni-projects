package project1;

import java.util.Objects;

public class Point {
    private Double x;
    private Double y;

    Point(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public static Double distanceBetween(Point A, Point B) {
        Double dx = (B.getX() - A.getX()) * (B.getX() - A.getX());
        Double dy = (B.getY() - A.getY()) * (B.getY() - A.getY());
        return Math.sqrt(dx + dy);
    }

    public static Double slope(Point A, Point B) {
        return Math.abs((B.getY() - A.getY()) / (B.getX() - A.getX()));
    }

    public static boolean threePointsOnOneSlope(Point A, Point B, Point C) {
        return ((Objects.equals(A.getX(), B.getX()) && Objects.equals(A.getX(), C.getX())) || (Objects.equals(A.getY(), B.getY()) && Objects.equals(A.getY(), C.getY())));
    }
}
