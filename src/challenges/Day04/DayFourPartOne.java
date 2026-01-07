package challenges.Day04;

import static util.Utils.readFileAsGrid;

public class DayFourPartOne {

    private static final char ACTIVE = '@';
    private static final char EMPTY  = '.';
    private static final int MIN_NEIGHBORS = 4;

    public Integer getMoreRoll() {
        char[][] grid = readFileAsGrid("challenge-four.txt");

        return removeWeakCellsInRounds(grid);
    }

    private int removeWeakCellsInRounds(char[][] initial) {
        char[][] grid = deepCopy(initial);
        int removedTotal = 0;

        while (true) {
            char[][] next = deepCopy(grid);
            int removedThisRound = 0;

            for (int r = 0; r < grid.length; r++) {
                for (int c = 0; c < grid[r].length; c++) {
                    if (grid[r][c] == ACTIVE && countNeighbors(grid, r, c) < MIN_NEIGHBORS) {
                        next[r][c] = EMPTY;
                        removedThisRound++;
                    }
                }
            }

            if (removedThisRound == 0) break;

            removedTotal += removedThisRound;
            grid = next;
        }

        return removedTotal;
    }

    private int countNeighbors(char[][] grid, int row, int col) {
        int count = 0;

        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;

                int r = row + dr;
                int c = col + dc;

                if (r < 0 || c < 0 || r >= grid.length || c >= grid[r].length) continue;
                if (grid[r][c] == ACTIVE) count++;
            }
        }

        return count;
    }

    private char[][] deepCopy(char[][] grid) {
        char[][] copy = new char[grid.length][];
        for (int r = 0; r < grid.length; r++) {
            copy[r] = grid[r].clone();
        }
        return copy;
    }
}
