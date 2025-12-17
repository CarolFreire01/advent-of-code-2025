package challenges.Day03;

import java.util.List;

import static util.Utils.getNumbersFile;

public class ChallengeSix {

    public long getRightTotalJoltage() {
        List<String> listBatteryBank = getNumbersFile("/challenge-three.txt");
        long totalJoltage = 0;

        for (String line : listBatteryBank) {
            long bestJoltage = getJoltage(line);
            totalJoltage += bestJoltage;
        }

        return totalJoltage;
    }

    private static long getJoltage(String line) {
        long result = 0;
        int start = 0;

        for (int remaining = 12; remaining > 0; remaining--) {
            int maxDigit = -1;
            int maxIdx = -1;
            int lastValidNumber = line.length() - remaining;

            for (int i = start; i <= lastValidNumber; i++) {
                int digit = line.charAt(i) - '0';
                if (digit > maxDigit) {
                    maxDigit = digit;
                    maxIdx = i;
                    if (digit == 9) {
                        break;
                    }
                }
            }

            result = result * 10 + maxDigit;
            start = maxIdx + 1;
        }

        return result;
    }

}
