package challenges.DayOne;

import java.util.List;

import static util.Utils.getNumber;
import static util.Utils.getValueFile;

public class ChallengeTwo {

    private static final int MAX_VALUE = 100;
    private static int PASSWORD = 0;

    // Each time pass at the point 0 during the rotation and the result is zero, you'll increase +1 (each time too) of password.
    public Integer getPasswordDuringRotation() {
        List<String> listDirections = getValueFile("/challenge-one.txt");
        int currentPoint = 50;

        for (String listDirection : listDirections) {
            int number = getNumber(listDirection);
            char dir = listDirection.charAt(0);

            int steps = dir == 'R' ? number : -number;

            PASSWORD += countZeroHits(currentPoint, steps);

            currentPoint = ((currentPoint + steps) % 100 + 100) % 100;
        }


        return PASSWORD;
    }

    private int countZeroHits(int startPosition, int steps) {
        if (steps == 0) return 0;

        startPosition = ((startPosition % MAX_VALUE) + MAX_VALUE) % MAX_VALUE;

        int absSteps = Math.abs(steps);
        int stepsToFirstZero = calculateStepsToZero(steps > 0, startPosition);

        if (absSteps < stepsToFirstZero) {
            return 0;
        }

        int remainingSteps = absSteps - stepsToFirstZero;

        return 1 + remainingSteps / MAX_VALUE;
    }

    private int calculateStepsToZero(boolean clockwise, int currentPosition) {
        if (currentPosition == 0) {
            return MAX_VALUE;
        }

        return clockwise ? MAX_VALUE - currentPosition : currentPosition;
    }
}