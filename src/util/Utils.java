package util;

import challenges.DayTwo.support.Pair;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

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
        int cols = lines.get(0).length(); // todas as linhas têm o mesmo tamanho

        char[][] grid = new char[rows][cols];

        for (int row = 0; row < rows; row++) {
            grid[row] = lines.get(row).toCharArray();
        }

        return grid;
    }


    private static InputStream getInputStream(String fileName) {
        return Utils.class.getClassLoader().getResourceAsStream(BASE_PATH + fileName);
    }

    public static Integer getNumber(String instruction) {
        String number = instruction.substring(1);
        return Integer.valueOf(number);
    }
}