package project2;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;

public class Player {
    private static int maleCounter = 0;
    private static int femaleCounter = 0;

    private final String name;
    private final String surname;
    private final LocalDate birthday;
    private final String pesel;
    private final char gender;

    private final HashMap<String, String> results = new HashMap<>();

    Player(String name, String surname, String birthday) {
        this.name = name;
        this.surname = surname;
        this.birthday = LocalDate.parse(birthday); //birthday should be passed with format "yyyy-mm-dd"
        this.pesel = generatePesel();
        this.gender = determineGender(this.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player player) {
            return Objects.equals(this.pesel, player.pesel);
        } else
            return false;
    }

    @Override
    public String toString() {
        return this.getName() + " " + this.getSurname() + " " + this.getFirstLapResultsInMinutes() + " " + this.getSecondLapResultsInMinutes() + " " + this.getFinalResultsInMinutes();
    }

    private char determineGender(String name) {
        if (name.charAt(name.length()-1) =='a') {
            return 'f';
        }
        return 'm';
    }

    private String generatePesel() {
        int year = this.birthday.getYear();
        int month = this.birthday.getMonthValue();
        int day = this.birthday.getDayOfMonth();

        String monthString="";
        StringBuilder builder = new StringBuilder();
        builder.append(fillMissingDigitsWithZeros(year%100, 2));

        if (year>=1800 && year<=1899) {
            monthString = String.valueOf(month+80);
        } else if (year>=1900 && year<=1999) {
            monthString = fillMissingDigitsWithZeros(month, 2);
        } else if (year>=2000 && year<=2099) {
            monthString = String.valueOf(month+20);
        } else if (year>=2100 && year<=2199) {
            monthString = String.valueOf(month+40);
        } else if (year>=2200 && year<=2299) {
            monthString = String.valueOf(month+60);
        }

        builder.append(monthString);
        builder.append(fillMissingDigitsWithZeros(day, 2));

        builder.append(generateSerialNumber());
        builder.append(generateControlNumber(builder.toString()));

        return builder.toString();
    }

    private String generateSerialNumber() {
        StringBuilder builder = new StringBuilder();
        if (this.determineGender(this.name) == 'f') {
            builder.append(fillMissingDigitsWithZeros(femaleCounter, 3));
            femaleCounter++;

            int random = (int) (Math.random()*4);
            random *= (random%2==0 ? 1 : 2);

            builder.append(random);
        } else {
            builder.append(fillMissingDigitsWithZeros(maleCounter, 3));
            maleCounter++;

            int random = (int) (Math.random()*8);
            random += (random%2==0 ? 1 : 0);

            builder.append(random);
        }

        return builder.toString();
    }

    private String fillMissingDigitsWithZeros(int number, int sizeGoal) {
        StringBuilder builder = new StringBuilder();
        builder.append(number);

        if (builder.toString().length() < sizeGoal) {
            builder.reverse();
            while (builder.toString().length() < sizeGoal) {
                builder.append("0");
            }
            builder.reverse();
        }
        return builder.toString();
    }

    private int generateControlNumber(String pesel) {
        int[] weights = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
        int sum = 0;
        for (int i=0; i<pesel.length(); i++) {
            sum += Integer.parseInt(String.valueOf(pesel.charAt(i)))*weights[i];
        }

        int m = sum%10;
        if (sum == 0)
            return 0;
        else
            return 10-m;
    }

    public String getAgeCategory() {
        int age = LocalDate.now().getYear()-this.birthday.getYear();
        String letter;
        String category;

        if (this.gender =='f')
            letter = "W";
        else
            letter = "M";

        if (age>0 && age<20)
            category = "1";
        else if (age>=20 && age<30)
            category = "2";
        else if (age>=30 && age<40)
            category = "3";
        else
            category = "4";

        return letter+category;
    }

    public int getAge() {
        return LocalDate.now().getYear()-this.birthday.getYear();
    }

    public static int compareByBirthday(LocalDate p1, LocalDate p2) {
        return p1.compareTo(p2);
    }

    public void generateResults() {
        long total = 0;
        //assuming that the minimum time required for swimming is 10 minutes, max - 30
        int swimMinutes = (int) (Math.random()*ActivitiesTimeLimits.SWIM_MAX.value+ActivitiesTimeLimits.SWIM_MIN.value);
        int swimSeconds = (int) (Math.random()*59);

        total+=swimMinutes* 60L+swimSeconds;
        this.results.put("swim", convertSecondsToHoursMinutesSeconds(total));

        //assuming that the minimum time required for riding is 30 minutes, max - 80
        int bikeMinutes = (int) (Math.random()*ActivitiesTimeLimits.BIKE_MAX.value+ActivitiesTimeLimits.BIKE_MIN.value);
        int bikeSeconds = (int) (Math.random()*59);

        total+=bikeMinutes* 60L+bikeSeconds;
        this.results.put("bike", convertSecondsToHoursMinutesSeconds(total));

        //assuming that the minimum time required for running is 15 minutes, max - 60
        int runMinutes = (int) (Math.random()*ActivitiesTimeLimits.RUN_MAX.value+ActivitiesTimeLimits.RUN_MIN.value);
        int runSeconds = (int) (Math.random()*59);

        total+=runMinutes* 60L+runSeconds;
        this.results.put("run", convertSecondsToHoursMinutesSeconds(total));
    }

    private String convertSecondsToHoursMinutesSeconds(long seconds) {
        return String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60);
    }

    public Duration getFinalResultsDuration() {
        String[] values = this.results.get("run").split(":");
        Duration duration = Duration.ofHours(Integer.parseInt(values[0]));
        duration = duration.plusMinutes(Integer.parseInt(values[1]));
        duration = duration.plusSeconds(Integer.parseInt(values[2]));

        return duration;
    }

    public Duration getSwimResultsDuration() {
        String[] values = this.results.get("swim").split(":");
        Duration duration = Duration.ofHours(Integer.parseInt(values[0]));
        duration = duration.plusMinutes(Integer.parseInt(values[1]));
        duration = duration.plusSeconds(Integer.parseInt(values[2]));

        return duration;
    }

    public Duration getBikeResultsDuration() {
        String[] values = this.results.get("bike").split(":");

        Duration durationSwimAndBike = Duration.ofHours(Integer.parseInt(values[0]));
        durationSwimAndBike = durationSwimAndBike.plusMinutes(Integer.parseInt(values[1]));
        durationSwimAndBike = durationSwimAndBike.plusSeconds(Integer.parseInt(values[2]));

        Duration durationSwim = this.getSwimResultsDuration();

        return durationSwimAndBike.minus(durationSwim);
    }

    public Duration getRunResultsDuration() {
        return this.getFinalResultsDuration().minus(this.getBikeResultsDuration());
    }

    private String durationInSecondsFormatted (Duration duration) {
        return String.format("%.2f",
                (duration.toMillis()/60000.0));
    }

    public String getFirstLapResultsInMinutes() {
        return durationInSecondsFormatted(getSwimResultsDuration());
    }

    public String getSecondLapResultsInMinutes() {
        return durationInSecondsFormatted(getSwimResultsDuration().plus(getBikeResultsDuration()));
    }

    public String getFinalResultsInMinutes() {
        return durationInSecondsFormatted(getSwimResultsDuration().plus(getBikeResultsDuration()).plus(getRunResultsDuration()));
    }

    public String getSwimResultsInMinutes() {
        return durationInSecondsFormatted(getSwimResultsDuration());
    }

    public String getBikeResultsInMinutes() {
        return durationInSecondsFormatted(getBikeResultsDuration());
    }

    public String getRunResultsInMinutes() {
        return durationInSecondsFormatted(getRunResultsDuration());
    }

    public static int compareBySwimResults(Player p1, Player p2) {
        return p1.getSwimResultsDuration().compareTo(p2.getSwimResultsDuration());
    }

    public static int compareByBikeResults(Player p1, Player p2) {
        return p1.getBikeResultsDuration().compareTo(p2.getBikeResultsDuration());
    }

    public static int compareByRunResults(Player p1, Player p2) {
        return p1.getRunResultsDuration().compareTo(p2.getRunResultsDuration());
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getPesel() {
        return pesel;
    }

    public char getGender() {
        return gender;
    }
}
