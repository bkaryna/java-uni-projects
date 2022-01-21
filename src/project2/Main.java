package project2;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader;
        ArrayList<Player> players = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/project2/data.txt"));
            //read data from file
            String line = reader.readLine();
            while (line != null) {
                String[] values = line.split(";");
                //create player and add to the players list
                players.add(new Player(values[0], values[1], values[2] + "-" + (values[3].length()<2 ? "0"+values[3] : values[3]) + "-" + (values[4].length()<2 ? "0"+values[4] : values[4])));
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());;
        }

        //generate random competition results
        for (Player player:players) {
            player.generateResults();
        }

        //sort by final results
        players.sort(new PlayerResultComparator());

        //swim rank
        List<Integer> swimRank = players
                .stream()
                .map(p-> players.stream().sorted(Player::compareBySwimResults).toList().indexOf(p))
                .collect(Collectors.toList());

        //bike rank
        List<Integer> bikeRank = players
                .stream()
                .map(p-> players.stream().sorted(Player::compareByBikeResults).toList().indexOf(p))
                .collect(Collectors.toList());

        //run rank
        List<Integer> runRank = players
                .stream()
                .map(p-> players.stream().sorted(Player::compareByRunResults).toList().indexOf(p))
                .collect(Collectors.toList());

        //male rank
        List<Integer> maleRank = players
                .stream()
                .filter(p-> p.getGender() == 'm')
                .map(p-> players.indexOf(p) )
                .collect(Collectors.toList());

        //female rank
        List<Integer> femaleRank = players
                .stream()
                .filter(p-> p.getGender() == 'f')
                .map(p-> players.indexOf(p) )
                .collect(Collectors.toList());

        //age-gender ranks
        //M1 rank
        List<Integer> m1Rank = players
                .stream()
                .filter(p-> Objects.equals(p.getAgeCategory(), "M1"))
                .map(p-> players.indexOf(p) )
                .collect(Collectors.toList());

        //M2 rank
        List<Integer> m2Rank = players
                .stream()
                .filter(p-> Objects.equals(p.getAgeCategory(), "M2"))
                .map(p-> players.indexOf(p) )
                .collect(Collectors.toList());

        //M3 rank
        List<Integer> m3Rank = players
                .stream()
                .filter(p-> Objects.equals(p.getAgeCategory(), "M3"))
                .map(p-> players.indexOf(p) )
                .collect(Collectors.toList());

        //M4 rank
        List<Integer> m4Rank = players
                .stream()
                .filter(p-> Objects.equals(p.getAgeCategory(), "M4"))
                .map(p-> players.indexOf(p) )
                .collect(Collectors.toList());

        //W1 rank
        List<Integer> w1Rank = players
                .stream()
                .filter(p-> Objects.equals(p.getAgeCategory(), "W1"))
                .map(p-> players.indexOf(p) )
                .collect(Collectors.toList());

        //W2 rank
        List<Integer> w2Rank = players
                .stream()
                .filter(p-> Objects.equals(p.getAgeCategory(), "W2"))
                .map(p-> players.indexOf(p) )
                .collect(Collectors.toList());

        //W3 rank
        List<Integer> w3Rank = players
                .stream()
                .filter(p-> Objects.equals(p.getAgeCategory(), "W3"))
                .map(p-> players.indexOf(p) )
                .collect(Collectors.toList());

        //M4 rank
        List<Integer> w4Rank = players
                .stream()
                .filter(p-> Objects.equals(p.getAgeCategory(), "W4"))
                .map(p-> players.indexOf(p) )
                .collect(Collectors.toList());

        //report
        File reportFile = new File("src/project2/report.txt");
        try {
            FileWriter fileWriter = new FileWriter(reportFile);
            fileWriter.write("Nr\tName\tSurname\t1st lap\t2nd lap\tFinish\tGender\tCategory\tSwim\tBike\tRun");

            for (int i=0; i<players.size(); i++) {
                String category = players.get(i).getAgeCategory();
                String categoryRank="";

                switch(category) {
                    case "M1" -> categoryRank = "M1-" + (m1Rank.indexOf(i) + 1);
                    case "M2" -> categoryRank = "M2-" + (m2Rank.indexOf(i) + 1);
                    case "M3" -> categoryRank = "M3-" + (m3Rank.indexOf(i) + 1);
                    case "M4" -> categoryRank = "M4-" + (m4Rank.indexOf(i) + 1);

                    case "W1" -> categoryRank = "W1-" + (w1Rank.indexOf(i) + 1);
                    case "W2" -> categoryRank = "W2-" + (w2Rank.indexOf(i) + 1);
                    case "W3" -> categoryRank = "W3-" + (w3Rank.indexOf(i) + 1);
                    case "W4" -> categoryRank = "W4-" + (w4Rank.indexOf(i) + 1);
                }

                fileWriter.write("\n" + (i+1) + "\t" + players.get(i) + " " + (players.get(i).getGender() == 'm' ? ("M-"+(maleRank.indexOf(i)+1)) : ("W-"+(femaleRank.indexOf(i)+1)))
                        + " " + categoryRank + " " + (swimRank.indexOf(i)+1) + " " + (bikeRank.indexOf(i)+1) + " " + (runRank.indexOf(i)+1));
            }
            fileWriter.close();
            System.out.println("Finished writing data to report");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //competition winner
        Player winner = players.get(0);
        System.out.println("Winner: " + winner.getName() + " " + winner.getSurname() + " " + winner.getPesel());

        //question 1
        //oldest person
        LocalDate oldestBirthday = players
                .stream()
                .map(p-> p.getBirthday())
                .min(Player::compareByBirthday)
                .get();

        Player oldestPlayer = players
                .stream()
                .filter(p->p.getBirthday().compareTo(oldestBirthday)==0)
                .findAny().orElse(null);

        System.out.println("The oldest: " + oldestPlayer.getName() + " " + oldestPlayer.getSurname() + " " + oldestPlayer.getPesel());

        //youngest person
        LocalDate youngestBirthday = players
                .stream()
                .map(p-> p.getBirthday())
                .max(Player::compareByBirthday)
                .get();

        Player youngestPlayer = players
                .stream()
                .filter(p->p.getBirthday().compareTo(youngestBirthday)==0)
                .findAny().orElse(null);

        System.out.println("The youngest: " + youngestPlayer.getName() + " " + youngestPlayer.getSurname() + " " + youngestPlayer.getPesel());

        //question 2 - please check lines 39-55 of Main.java (swim rank, ride rank, run rank)
        //best swimmer
        Integer bestSwimmerIndex = swimRank.stream().findFirst().get();
        Player bestSwimmer = players
                .stream()
                .filter(p -> players.indexOf(p) == bestSwimmerIndex)
                .findFirst()
                .get();
        System.out.println("Best swimmer: " + bestSwimmer.getName() + " " + bestSwimmer.getSurname() + " " + bestSwimmer.getPesel() + " " + bestSwimmer.getSwimResultsInMinutes());

        //best rider
        Integer bestBikerIndex = bikeRank.stream().findFirst().get();
        Player bestBiker = players
                .stream()
                .filter(p -> players.indexOf(p) == bestBikerIndex)
                .findFirst()
                .get();
        System.out.println("Best biker: " + bestBiker.getName() + " " + bestBiker.getSurname() + " " + bestBiker.getPesel() + " " + bestBiker.getBikeResultsInMinutes());

        //best runner
        Integer bestRunnerIndex = runRank.stream().findFirst().get();
        Player bestRunner = players
                .stream()
                .filter(p -> players.indexOf(p) == bestRunnerIndex)
                .findFirst()
                .get();
        System.out.println("Best rider: " + bestRunner.getName() + " " + bestRunner.getSurname() + " " + bestRunner.getPesel() + " " + bestRunner.getRunResultsInMinutes());

        //question 3
        //men average
        Double menAverage = players
                .stream()
                .filter(p-> p.getGender() == 'm')
                .mapToDouble(p-> Double.parseDouble(p.getFinalResultsInMinutes()))
                .average()
                .getAsDouble();
        System.out.format("\nMen average: %.2f", menAverage);

        //women average
        Double womenAverage = players
                .stream()
                .filter(p-> p.getGender() == 'f')
                .mapToDouble(p-> Double.parseDouble(p.getFinalResultsInMinutes()))
                .average()
                .getAsDouble();
        System.out.format("\nWomen average: %.2f", womenAverage);

        //question 4
        //W1 swim time sum
        Double w1SwimSum = players
                .stream()
                .filter(p-> Objects.equals(p.getAgeCategory(), "W1"))
                .mapToDouble(p-> Double.parseDouble(p.getSwimResultsInMinutes()))
                .sum();
        System.out.format("\nW1 swim sum: %.2f", w1SwimSum);

        //M4 swim time sum
        Double m4SwimSum = players
                .stream()
                .filter(p-> Objects.equals(p.getAgeCategory(), "M4"))
                .mapToDouble(p-> Double.parseDouble(p.getSwimResultsInMinutes()))
                .sum();
        System.out.format("\nM4 swim sum: %.2f", m4SwimSum);

        //question 5
        LocalDate podiumOldest = players
                .stream()
                .filter(p-> players.indexOf(p) >=0 && players.indexOf(p) <=2)
                .map(p-> p.getBirthday())
                .sorted(Player::compareByBirthday)
                .findFirst().orElse(null);

        Player oldestPlayerOnPodium = players
                .stream()
                .filter(p-> players.indexOf(p) >=0 && players.indexOf(p) <=2 && p.getBirthday().equals(podiumOldest))
                        .findFirst().orElse(null);

        System.out.println("\nThe oldest on podium is: " + oldestPlayerOnPodium.getName() + " " + oldestPlayerOnPodium.getSurname() + " " + oldestPlayerOnPodium.getPesel());

        //question 6
        List<Month> birthdayMonths = players
                .stream()
                .map(p->p.getBirthday().getMonth())
                .distinct()
                .collect(Collectors.toList());

        HashMap<Month, Double> monthAverages = new HashMap<Month, Double>();
        for (int i=0; i<birthdayMonths.size(); i++) {
            int finalI = i;
            Double average = players
                    .stream()
                    .filter(p-> p.getBirthday().getMonth().equals(birthdayMonths.get(finalI)))
                    .mapToDouble(p-> Double.parseDouble(p.getFinalResultsInMinutes()))
                    .average()
                            .orElse(0.0);
            monthAverages.put(birthdayMonths.get(finalI), average);
        }

        Map.Entry<Month, Double> maxAverage = monthAverages
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        System.out.format("%s %.2f min", maxAverage.getKey().toString(), maxAverage.getValue());
    }
}
