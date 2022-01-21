package project2;

import java.util.Comparator;

public class PlayerResultComparator implements Comparator<Player> {
    @Override
    public int compare(Player p1, Player p2) {
        return p1.getFinalResultsDuration().compareTo(p2.getFinalResultsDuration());
    }
}
