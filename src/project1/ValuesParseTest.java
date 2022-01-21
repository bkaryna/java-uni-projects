package project1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class ValuesParseTest {
    ArrayList<Figure> figures = new ArrayList<>();

    @Test
    void checkParse() {
        Assertions.assertThrows(InvalidFigure.class, () -> UserInput.parse(new ArrayList<>(2), figures));
        Assertions.assertThrows(InvalidFigure.class, () -> UserInput.parse(new ArrayList<>(), figures));

        Assertions.assertThrows(InvalidFigure.class, () -> UserInput.parse(new ArrayList<>(Arrays.asList(2.0, 3.0, 0.0)), figures));
        Assertions.assertThrows(InvalidFigure.class, () -> UserInput.parse(new ArrayList<>(Arrays.asList(1.0, 0.0, 2.0, 0.0, 3.0, 0.0)), figures));

        Assertions.assertThrows(InvalidFigure.class, () -> UserInput.parse(new ArrayList<>(Arrays.asList(1.0, 4.0, 2.0, 4.0, 3.0, 0.0, 2.0, 4.0)), figures));
        Assertions.assertThrows(InvalidFigure.class, () -> UserInput.parse(new ArrayList<>(Arrays.asList(1.0, 4.0, 2.0, 4.0, 3.0, 0.0, 2.0, 4.0, 1.0, 4.0, 2.0, 4.0, 3.0, 0.0, 2.0)), figures));

        try {
            figures.clear();
            UserInput.parse(new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 3.0, 3.0, 3.0, 3.0, 0.0)), figures);
            UserInput.parse(new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 5.0, 1.0, 9.0, 7.0, 4.0)), figures);
            UserInput.parse(new ArrayList<>(Arrays.asList(1.0, 1.0, 2.0, 4.0, 3.0, 3.0)), figures);

            UserInput.parse(new ArrayList<>(Arrays.asList(0.0, 0.0, 8.0, 6.0, 17.0, 6.0, 20.0, 0.0)), figures);
            UserInput.parse(new ArrayList<>(Arrays.asList(-5.0, 1.0, -4.0, 2.0, 0.0, 2.0, -1.0, 1.0)), figures);
            UserInput.parse(new ArrayList<>(Arrays.asList(-2.0, 1.0, -2.0, 3.0, 3.0, 3.0, 3.0, 1.0)), figures);

            UserInput.parse(new ArrayList<>(Arrays.asList(-2.0, 1.0, 2.0, 3.0, 0.0, -1.0, -4.0, -3.0)), figures);
            UserInput.parse(new ArrayList<>(Arrays.asList(1.0, 1.0, 5.0)), figures);
        } catch (InvalidFigure e) {
            //do nothing
        }

        Assertions.assertEquals(Square.class.getName(), figures.get(0).getClass().getName());
        Assertions.assertEquals(Quadrangle.class.getName(), figures.get(1).getClass().getName());
        Assertions.assertEquals(Triangle.class.getName(), figures.get(2).getClass().getName());
        Assertions.assertEquals(Trapezoid.class.getName(), figures.get(3).getClass().getName());
        Assertions.assertEquals(Parallelogram.class.getName(), figures.get(4).getClass().getName());
        Assertions.assertEquals(Rectangle.class.getName(), figures.get(5).getClass().getName());
        Assertions.assertEquals(Rhombus.class.getName(), figures.get(6).getClass().getName());
        Assertions.assertEquals(Circle.class.getName(), figures.get(7).getClass().getName());
    }
}

//data for testing from console
//0 0 0 3 3 3 3 0
//0 0 0 5 1 9 7 4
//1 1 2 4 3 3
//0 0 8 6 17 6 20 0
//1 1 5