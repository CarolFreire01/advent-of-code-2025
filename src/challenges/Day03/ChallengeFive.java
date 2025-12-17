package challenges.Day03;

import java.util.List;

import static util.Utils.getNumbersFile;

public class ChallengeFive {

    public Integer getTotalJoltage() {
        List<String> listBatteryBank = getNumbersFile("/challenge-three.txt");
        Integer totalJoltage = 0;

        for (String line : listBatteryBank) {
            Integer bestJoltage = getJoltage(line);
            totalJoltage += bestJoltage;
        }

        return totalJoltage;
    }

    private static Integer getJoltage(String line) {
        int bestJoltage = -1;
        int bestRight = -1;

        for (int i = line.length() - 1; i >= 0; i--) {
            int digit = line.charAt(i) - '0';

            if (bestRight != -1) {
                int candidate = digit * 10 + bestRight;
                bestJoltage = Math.max(bestJoltage, candidate);
            }

            if (digit > bestRight) {
                bestRight = digit;
            }
        }

        return bestJoltage;
    }
}
