package challenges.Day02;

import challenges.Day02.support.Pair;

import java.util.List;

import static util.Utils.getValueFileAndFormat;

public class ChallengeFour {

    // invalid ID are very number who is repeated -> 11-22
    // key word: range
    // number that repeat AT LEAST twice
    public long sumInvalidIds() {
        List<Pair> ranges = getValueFileAndFormat("/challenge-two.txt");

        long total = 0;
        for (Pair range : ranges) {
            total += sumInvalidIdsInRange(range.first(), range.second());
        }
        return total;
    }

    private long sumInvalidIdsInRange(long start, long end) {
        long sum = 0;

        for (long id = start; id <= end; id++) {
            if (isRepeatedPattern(id)) {
                sum += id;
            }
        }

        return sum;
    }

    /**
     * Returns true if the digits of the number are made by repeating a smaller block
     * at least twice. Examples: 11 (1 repeated), 1212 (12 repeated), 777777 (7 repeated).
     */
    private boolean isRepeatedPattern(long number) {
        String digits = Long.toString(number);
        int length = digits.length();

        // must repeat at least twice -> block length must be <= length/2
        if (length < 2) return false;

        for (int blockSize = 1; blockSize <= length / 2; blockSize++) {
            if (length % blockSize != 0) continue;

            if (isMadeOfRepeatedBlock(digits, blockSize)) {
                return true;
            }
        }

        return false;
    }

    private boolean isMadeOfRepeatedBlock(String digits, int blockSize) {
        String block = digits.substring(0, blockSize);

        for (int i = blockSize; i < digits.length(); i += blockSize) {
            if (!digits.regionMatches(i, block, 0, blockSize)) {
                return false;
            }
        }

        return true;
    }
}
