package project1;

import java.util.ArrayList;

public abstract class Figure implements Calculable {
    private Double[] values;

    Figure(Double...numbers) {
        values = new Double[numbers.length];
        int i = 0;
        for(Double number:numbers) {
            this.values[i] = number;
            i++;
        }
    }

    Figure(ArrayList<Double> numbers) {
        values = new Double[numbers.size()];
        int i = 0;
        for(Double number:numbers) {
            this.values[i] = number;
            i++;
        }
    }

    public void showInformation() {
        System.out.println("\nFigure information:\n\tFigure type: " + this.getClass().getSimpleName() + "\n\t" + this.calculateArea() + "\n\t" + this.calculatePerimeter());
    }

    public String calculateArea() {
        return "Couldn't calculate area for the figure. Too complex formula :<";
    }
}
