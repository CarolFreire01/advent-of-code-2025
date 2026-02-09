package challenges.Day10;

import java.io.IOException;
import java.net.BindException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static challenges.Day10.DayTenHelper.*;

public class DayTen {


    record BestSolution(AtomicInteger value) {
        static BestSolution initial() {
            return new BestSolution(new AtomicInteger(Integer.MAX_VALUE));
        }
    }

    public void dayTen() throws IOException {
        List<Machine> machines = readMachinesFromResource("challenge-ten.txt");

        System.out.println("Part One: " + pressButton(machines));
        System.out.println("Part Two: " + joltageLevels(machines));

    }

    private String joltageLevels(List<Machine> machines) {
        return "";
    }

    private int pressButton(List<Machine> machines) {
        int total = 0;

        for (Machine machine : machines) {
            int lights = toMask(machine.indicatorLight());
            int[] buttonMasks = toButtonMasks(machine.buttons());

            int min = minPresses(lights, buttonMasks);

            total += min;
        }

        return total;
    }

    private int minPresses(int lights, int[] buttons) {
        int buttonLength = buttons.length;
        int best = Integer.MAX_VALUE;

        int limit = 1 << buttonLength;

        for (int subset = 0; subset < limit; subset++) {
            int state = 0;

            for (int j = 0; j < buttonLength; j++) {
                if (((subset >> j) & 1) == 1) {
                    state ^= buttons[j];
                }
            }

            if (state == lights) {
                int presses = Integer.bitCount(subset);
                if (presses < best) best = presses;
            }
        }

        return (best == Integer.MAX_VALUE) ? -1 : best;
    }



}
