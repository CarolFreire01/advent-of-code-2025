package challenges.Day04;

import static util.Utils.readFileAsGrid;

public class DayFourPartTwo {

    private static final char ACTIVE = '@';
    private static final int MIN_NEIGHBORS = 4;

    public Integer getRoll() {
        char[][] grid = readFileAsGrid("challenge-four.txt");

        return countCells(grid);
    }

    private int countCells(char[][] grid) {
        int count = 0;

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                if (grid[r][c] == ACTIVE && countNeighbors(grid, r, c) < MIN_NEIGHBORS) {
                    count++;
                }
            }
        }

        return count;
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
}
