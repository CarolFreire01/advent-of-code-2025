package util;

import challenges.Day02.support.Pair;
import challenges.Day05.ParsedInput;
import challenges.Day06.ColumnOperator;
import challenges.Day09.DayNine;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    private static final String BASE_PATH = "files/";

    public static List<String> getValueFile(String fileName) {
        InputStream filePath = getInputStream(fileName);

        if (filePath == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(filePath, StandardCharsets.UTF_8))) {
            return br.lines().toList();
        } catch (Exception e) {
            throw new RuntimeException("Error reading file: " + fileName, e);
        }
    }

    public static List<Pair> getValueFileAndFormat(String fileName) {
        InputStream filePath = getInputStream(fileName);

        if (filePath == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(filePath, StandardCharsets.UTF_8))) {

            return br.lines()
                    .flatMap(line -> Arrays.stream(line.split(",")))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(s -> s.replaceAll("\\s+", ""))
                    .map(token -> {
                        String[] parts = token.split("-");
                        if (parts.length != 2) {
                            throw new IllegalArgumentException("Formato inválido: '" + token + "'");
                        }

                        return new Pair(
                                Long.parseLong(parts[0]),
                                Long.parseLong(parts[1])
                        );
                    })
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error reading file: " + fileName, e);
        }
    }

    public static DayNine.MovieTheater readMovieTheater(String fileName) {
        InputStream filePath = getInputStream(fileName);
        if (filePath == null) throw new IllegalArgumentException("File not found: " + fileName);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(filePath, StandardCharsets.UTF_8))) {

            List<int[]> pairs = br.lines()
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(line -> line.replaceAll("\\s+", "")) // remove espaços
                    .map(line -> {
                        String[] parts = line.split(",");
                        if (parts.length != 2) {
                            throw new IllegalArgumentException("Formato inválido: '" + line + "'");
                        }
                        return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
                    })
                    .toList();

            int[] xs = new int[pairs.size()];
            int[] ys = new int[pairs.size()];
            for (int i = 0; i < pairs.size(); i++) {
                xs[i] = pairs.get(i)[0];
                ys[i] = pairs.get(i)[1];
            }

            return new DayNine.MovieTheater(xs, ys);

        } catch (Exception e) {
            throw new RuntimeException("Error reading file: " + fileName, e);
        }
    }

    public static List<String> getNumbersFile(String fileName) {
        InputStream filePath = getInputStream(fileName);

        if (filePath == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(filePath, StandardCharsets.UTF_8))) {
            return br.lines()
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(String::toString)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error reading file: " + fileName, e);
        }
    }

    public static char[][] readFileAsGrid(String fileName) {
        InputStream filePath = getInputStream(fileName);

        if (filePath == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }

        List<String> lines;
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(filePath, StandardCharsets.UTF_8))) {
            lines = br.lines().toList();
        } catch (Exception e) {
            throw new RuntimeException("Error reading file: " + fileName, e);
        }

        if (lines.isEmpty()) {
            return new char[0][0];
        }

        int rows = lines.size();
        int cols = lines.getFirst().length();

        char[][] grid = new char[rows][cols];

        for (int row = 0; row < rows; row++) {
            grid[row] = lines.get(row).toCharArray();
        }

        return grid;
    }

    public static ParsedInput parseNumbers(String fileName) {
        InputStream filePath = getInputStream(fileName);

        if (filePath == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }

        List<Pair> ranges = new ArrayList<>();
        List<Long> singles = new ArrayList<>();

        boolean readingRanges = true;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(filePath, StandardCharsets.UTF_8))) {

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    readingRanges = false;
                    continue;
                }

                if (readingRanges) {
                    String[] parts = line.split("-");
                    if (parts.length != 2) {
                        throw new IllegalArgumentException("Invalid range: " + line);
                    }
                    Long start = Long.parseLong(parts[0]);
                    Long end   = Long.parseLong(parts[1]);
                    ranges.add(new Pair(start, end));
                } else {
                    singles.add(Long.parseLong(line));
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + fileName, e);
        }

        return new ParsedInput(ranges, singles);
    }

    public static List<Pair> mergeRanges(List<Pair> ranges) {
        List<Pair> normalized = ranges.stream()
                .map(r -> r.first() <= r.second() ? r : new Pair(r.second(), r.first()))
                .sorted((a, b) -> Long.compare(a.first(), b.first()))
                .toList();

        List<Pair> merged = new ArrayList<>();

        long start = normalized.getFirst().first();
        long end = normalized.getFirst().second();

        for (int i = 1; i < normalized.size(); i++) {
            Pair next = normalized.get(i);

            if (next.first() <= end) {
                end = Math.max(end, next.second());
            } else {
                merged.add(new Pair(start, end));
                start = next.first();
                end = next.second();
            }
        }

        merged.add(new Pair(start, end));
        return merged;
    }

    public static List<ColumnOperator> readColumnsWithOps(String fileName) {
        List<String> lines = Utils.getValueFile(fileName)
                .stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        String[] ops = lines.getLast().split("\\s+");
        int cols = ops.length;

        List<List<Long>> values = new ArrayList<>();
        for (int i = 0; i < cols; i++) values.add(new ArrayList<>());

        for (int i = 0; i < lines.size() - 1; i++) {
            String[] nums = lines.get(i).split("\\s+");
            for (int c = 0; c < cols; c++) {
                values.get(c).add(Long.parseLong(nums[c]));
            }
        }

        List<ColumnOperator> result = new ArrayList<>();
        for (int c = 0; c < cols; c++) {
            result.add(new ColumnOperator(ops[c].charAt(0), values.get(c)));
        }

        return result;
    }

    public static List<String> readRawLines(String fileName) {
        InputStream in = getInputStream(fileName);

        if (in == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(in, StandardCharsets.UTF_8))) {

            return br.lines().toList();

        } catch (Exception e) {
            throw new RuntimeException("Error reading file: " + fileName, e);
        }
    }

    public static InputStream getInputStream(String fileName) {
        return Utils.class.getClassLoader().getResourceAsStream(BASE_PATH + fileName);
    }

    public static Integer getNumber(String instruction) {
        String number = instruction.substring(1);
        return Integer.valueOf(number);
    }
}