package project1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {

        Logger logger = Logger.getLogger("Figures-Logger");
        logger.setUseParentHandlers(false); //disables log output to console

        FileHandler fh = null;
        try {
            fh = new FileHandler("figures-log.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.addHandler(fh);
        logger.info("Figures log creation");

        //get number of figures
        int n = 0;
        do {
            n = UserInput.getNumberOfFigures();
        } while (n < 1);
        System.out.println(n);

        ArrayList<Figure> figures;
        do {
            figures = UserInput.getValuesAndParse(n);
        } while (figures.isEmpty());

        for (Figure f : figures)
            f.showInformation();
    }
}
