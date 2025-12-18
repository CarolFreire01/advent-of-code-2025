package challenges.Day05;

import challenges.Day02.support.Pair;

import java.util.List;

import static util.Utils.mergeRanges;
import static util.Utils.parseNumbers;

public class DayFive {

    public void selectIngredient() {
        ParsedInput info = parseNumbers("challenge-five.txt");

        List<Pair> pairs = mergeRanges(info.ranges());

        System.out.println("Day Five -> Part 1: " + getQuantityFreshIngredients(pairs, info));
        System.out.println("Day Five -> Part 2: " + getQuantityFreshID(pairs));
    }

    public long getQuantityFreshID(List<Pair> pairs) {
        long total = 0;

        for (Pair pair: pairs) {
            total += (pair.second() - pair.first() + 1);
        }

        return total;
    }

    private static int getQuantityFreshIngredients(List<Pair> pairs, ParsedInput info) {
        int count = 0;

        for (Pair pair : pairs) {
            for (Long single : info.singles()) {
                if (single >= pair.first() && single <= pair.second()) {
                    count++;
                }
            }
        }
        return count;
    }
}