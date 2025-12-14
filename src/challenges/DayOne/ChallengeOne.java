package challenges.DayOne;

import java.util.List;

import static util.Utils.getNumber;
import static util.Utils.getValueFile;

public class ChallengeOne {

    private static final int MAX_VALUE = 100;
    private static int PASSWORD = 0;

    // Each time the end result of a rotation is equals 0, you'll increase the value of password.
    public Integer getPassword() {
        List<String> listDirections = getValueFile("/challenge-one.txt");
        int currentPoint = 50;

        for (String listDirection : listDirections) {
            int number = getNumber(listDirection);

            currentPoint = listDirection.startsWith("R") ? currentPoint + number : currentPoint - number;

            currentPoint = ((currentPoint % MAX_VALUE) + MAX_VALUE) % MAX_VALUE;

            if (currentPoint == 0)
                PASSWORD++;
        }

        return PASSWORD;
    }

}