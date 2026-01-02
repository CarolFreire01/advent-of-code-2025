package challenges.Day07;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;

import static util.Utils.readFileAsGrid;

public class DaySeven {

    private static final char S = 'S';

    public void daySeven() {
        char[][] grid = readFileAsGrid("challenge-seven.txt");

        System.out.println("Part 1 : " + countBeams(grid));
        System.out.println("Part 2 : " + countTimelines(grid));


    }

    private long countTimelines(char[][] grid) {
        Position start = findStart(grid);

        int height = grid.length;
        int width = grid[0].length;

        long[] current = new long[width];
        long[] next = new long[width];
        current[start.col()] = 1;

        for (int row = start.row() + 1; row < height; row++) {
            Arrays.fill(next, 0);

            propagateTimelines(grid, row, current, next);

            long[] temp = current;
            current = next;
            next = temp;
        }

        long sum = 0;
        for (long v : current) sum += v;
        return sum;

    }

    private void propagateTimelines(char[][] grid, int row, long[] current, long[] next) {
        int width = grid[0].length;

        for (int col = 0; col < width; col++) {
            long count = current[col];
            if (count == 0) continue;

            if (grid[row][col] == '^') {
                if (col > 0) next [col - 1] += count;
                if (col + 1 < width) next[col + 1] += count;
            } else {
                next[col] += count;
            }

        }
    }

    private int countBeams(char[][] grid) {
        HashSet<Long> splitters = new HashSet<>();
        Deque<Position> beams = new ArrayDeque<>();

        Position start = findStart(grid);
        beams.add(new Position(start.row() + 1, start.col()));

        while (!beams.isEmpty()) {
            Position beam = beams.poll();

            if (beam.row() >= grid.length) continue;

            int splitterRow = findSplitterRow(grid, beam.row(), beam.col());
            if (splitterRow == - 1) continue;

            long key = encode(splitterRow, beam.col());
            if (!splitters.add(key)) continue;

            int nextRow = splitterRow + 1;

            if (beam.col() > 0) {
                beams.add(new Position(nextRow, beam.col() - 1));
            }

            if(beam.col() + 1 < grid[0].length) {
                beams.add(new Position(nextRow, beam.col() + 1));
            }

        }

        return splitters.size();
    }

    private long encode(int row, int col) {
        return ((long) row << 32) | (col & 0xffffffffL);
    }

    private int findSplitterRow(char[][] grid, int startRow, int col) {
        for (int row = startRow; row < grid.length; row++) {
            if (grid[row][col] == '^') {
                return row;
            }
        }
        return -1;
    }

    private Position findStart(char[][] grid) {

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == S) {
                    return new Position(row, col);
                }
            }
        }

        throw new IllegalStateException("Start position 'S' not found");
    }

}