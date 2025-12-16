package challenges.DayTwo;

import challenges.DayTwo.support.Pair;

import java.util.List;

import static util.Utils.getValueFileAndFormat;

public class ChallengeThree {

    // invalid ID are very number who is repeated -> 11-22
    // key word: range
    public long getAndCalculateInvalidId() {
        List<Pair> ranges = getValueFileAndFormat("/challenge-two.txt");
        long finalValue = 0;

        for (Pair pair : ranges) {
            finalValue += sumRepeatNumbers(pair.first(), pair.second());
        }

        return finalValue;
    }

    private long sumRepeatNumbers(long start, long end) {
        long sum = 0;
        for (long n = start; n <= end; n++) {
            if (isRepeatedNumber(n)) sum += n;
        }
        return sum;
    }

    private boolean isRepeatedNumber(long number) {
        String s = Long.toString(number);
        int len = s.length();
        if (len % 2 != 0) return false;

        int half = len / 2;
        return s.substring(0, half).equals(s.substring(half));
    }
}
