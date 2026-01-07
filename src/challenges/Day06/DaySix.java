package challenges.Day06;

import java.util.ArrayList;
import java.util.List;

import static util.Utils.readColumnsWithOps;
import static util.Utils.readRawLines;

public class DaySix {

    private static final char MULTIPLY = '*';
    private static final char PLUS = '+';

    public void getCephalopod() {
        List<ColumnOperator> columnOperators = readColumnsWithOps("challenge-six.txt");

        System.out.println("Part 1: " + calcCephalopod(columnOperators));
        System.out.println("Part 2: " + calcCephalopodRightToLeft());
    }

    public long calcCephalopod(List<ColumnOperator> columnOperators) {
        long calc = 0L;

        for (ColumnOperator col : columnOperators) {
            long result = getResult(col.op(), col.values());
            calc += result;
        }

        return calc;
    }

    public static long calcCephalopodRightToLeft() {
        List<List<Character>> grid = readGrid("challenge-six.txt");

        List<Integer> emptyCols = findEmptyCols(grid);

        long sum = 0L;
        int leftCol = 0;

        for (int rightCol : emptyCols) {
            ColumnOperator problem = parseProblemFromBlock(grid, leftCol, rightCol);

            if (problem != null) {
                long value = getResult(problem.op(), problem.values());
                sum += value;
            }

            leftCol = rightCol;
        }
        return sum;

    }

    private static ColumnOperator parseProblemFromBlock(List<List<Character>> grid, int leftCol, int rightCol) {
        int rows = grid.size();
        int opRow = rows - 1;

        char operator = 0;
        for (int col = leftCol; col < rightCol; col++) {
            char c = grid.get(opRow).get(col);
            if (c == PLUS || c == MULTIPLY) {
                operator = c;
                break;
            }
        }

        if (operator == 0) return null;

        List<Long> operands = new ArrayList<>();

        for (int col = leftCol; col < rightCol; col++) {
            StringBuilder sb = new StringBuilder();

            for (int row = 0; row < opRow; row++) {
                char c = grid.get(row).get(col);
                if (c != ' ') sb.append(c);
            }

            if (sb.isEmpty()) continue;

            operands.add(Long.parseLong(sb.toString()));
        }

        return new ColumnOperator(operator, operands);
    }

    private static List<Integer> findEmptyCols(List<List<Character>> grid) {
        List<Integer> emptyCols = new ArrayList<>();
        int rows = grid.size();
        int cols = grid.getFirst().size();

        for (int col = 0; col < cols; col++) {
            boolean isEmpty = true;
            for (int row = 0; row < rows; row++) {
                if (grid.get(row).get(col) != ' ') {
                    isEmpty = false;
                    break;
                }
            }
            if (isEmpty) {
                emptyCols.add(col);
            }
        }

        emptyCols.add(cols);
        return emptyCols;
    }

    private static List<List<Character>> readGrid(String fileName) {
        List<String> lines = readRawLines(fileName);

        List<String> cleaned = lines.stream()
                .filter(s -> !s.isBlank())
                .toList();

        int maxLen = cleaned.stream().mapToInt(String::length).max().orElse(0);

        List<List<Character>> grid = new ArrayList<>();
        for (String line : cleaned) {
            List<Character> row = new ArrayList<>(maxLen);
            for (int i = 0; i < maxLen; i++) {
                char ch = (i < line.length()) ? line.charAt(i) : ' ';
                row.add(ch);
            }
            grid.add(row);
        }
        return grid;
    }

    private static long getResult(char op, List<Long> nums) {
        long result = 0L;

        if (op == MULTIPLY) {
            result = nums.stream().reduce(1L, (a, b) -> a * b);
        } else if (op == PLUS) {
            result = nums.stream().mapToLong(Long::longValue).sum();
        }
        return result;
    }
}
