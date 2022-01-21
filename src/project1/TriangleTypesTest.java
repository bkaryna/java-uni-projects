package project1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class TriangleTypesTest {
    @Test
    void checkType() {
        Triangle triangle = new Triangle(new ArrayList<>(Arrays.asList(6.0, 4.0, -5.0, -3.0, -6.0, 8.0)));
        Assertions.assertEquals("\tType based on sides: " + "scalene" + "; type based on angles: " + "acute-angled", triangle.type());

        triangle = new Triangle(new ArrayList<>(Arrays.asList(16.0, 4.0, 8.0, 4.0, 8.0, 12.0)));
        Assertions.assertEquals("\tType based on sides: " + "isosceles" + "; type based on angles: " + "right", triangle.type());

        triangle = new Triangle(new ArrayList<>(Arrays.asList(12.0, 14.0, 24.0, 8.0, 2.0, 10.0)));
        Assertions.assertEquals("\tType based on sides: " + "scalene" + "; type based on angles: " + "obtuse-angled", triangle.type());

        triangle = new Triangle(new ArrayList<>(Arrays.asList(3.0, 0.0, 9.0 / 2, (3 * Math.sqrt(3)) / 2.0, 6.0, 0.0)));
        Assertions.assertEquals("\tType based on sides: " + "equilateral" + "; type based on angles: " + "acute-angled", triangle.type());
    }
}
