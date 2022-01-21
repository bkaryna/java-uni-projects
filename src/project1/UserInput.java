package project1;

import java.util.*;
import java.util.logging.Logger;

public abstract class UserInput {

    public static int getNumberOfFigures() {
        int n = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Number of figures: ");
        try {
            n = scanner.nextInt();
            if (n < 1)
                System.err.println("Number of figures should be > 0");
        } catch (
                InputMismatchException e) {
            System.err.println("Number of figures should be natural");
        }
        return n;
    }

    public static ArrayList<Figure> getValuesAndParse(int n) {
        ArrayList<Figure> figures = new ArrayList<>();

        System.out.println("\nPlease provide values for the figures. Values should be separated by whitespace. " +
                "Values for quadrangle points should be provided in (counter-)clockwise direction " +
                "\nExample for a rectangle:\n" + "\tA--------->B\n\t^          |\n\t|          v\n\tC<---------D\n"
                + "Press enter to go to the next figure/finish");

        for (int i = 0; i < n; i++) {
            System.out.println("\nValues for the figure number " + (i + 1) + ": ");

            Scanner scanner = new Scanner(System.in);
            ArrayList<Double> values = new ArrayList<>();

            String line = scanner.nextLine();

            try {
                String[] lineSplit = line.split(" ");

                for (String s : lineSplit) {
                    values.add(Double.parseDouble(s));
                }
                parse(values, figures);
            } catch (NumberFormatException e) {
                System.err.println("Illegal input format.");
                figures.clear();
                break;
            } catch (InvalidFigure e) {
                System.err.println(e.getMessage());
                figures.clear();
                break;
            }
        }
        return figures;
    }

    protected static void parse(ArrayList<Double> values, ArrayList<Figure> figures) throws InvalidFigure {
        Logger logger = Logger.getLogger("Figures-Logger");

        if (values.size() < 3 || values.size() > 8) {
            throw new InvalidFigure("This program cannot build a figure with the given number of points.");
        } else {
            switch (values.size()) {
                case 3 -> {
                    if (values.get(2) <= 0)
                        throw new InvalidFigure("Circle diameter cannot be <=0");
                    figures.add(new Circle(values));
                    logger.info("New Circle with values: " + values);
                }
                case 6 -> {
                    Triangle.isTriangle(values);
                    figures.add(new Triangle(values));
                    logger.info("New Triangle with values: " + values);
                }
                case 8 -> {
                    Quadrangle figure = new Quadrangle(values);
                    String type = figure.determineType();

                    switch (type) {
                        case "Trapezoid" -> {
                            figures.add(new Trapezoid(values));
                            logger.info("New Trapezoid with values: " + values);
                        }
                        case "Parallelogram" -> {
                            figures.add(new Parallelogram(values));
                            logger.info("New Parallelogram with values: " + values);
                        }
                        case "Rhombus" -> {
                            figures.add(new Rhombus(values));
                            logger.info("New Rhombus with values: " + values);
                        }
                        case "Square" -> {
                            figures.add(new Square(values));
                            logger.info("New Square with values: " + values);
                        }
                        case "Rectangle" -> {
                            figures.add(new Rectangle(values));
                            logger.info("New Rectangle with values: " + values);
                        }
                        case "Quadrangle" -> {
                            figures.add(new Quadrangle(values));
                            logger.info("New Quadrangle with values: " + values);
                        }
                        case "error" -> throw new InvalidFigure("Three points cannot be on the same slope");
                    }
                }
                default -> throw new InvalidFigure("This program cannot build a figure with the given number of points.");
            }
        }
    }
}
