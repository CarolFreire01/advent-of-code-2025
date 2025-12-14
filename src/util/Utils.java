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
                    .filter(s -> !s.isEmpty())              // ✅ remove tokens vazios (causados por vírgula final)
                    .map(s -> s.replaceAll("\\s+", ""))     // ✅ remove espaços no meio, se existirem
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

    private static InputStream getInputStream(String fileName) {
        return Utils.class.getClassLoader().getResourceAsStream(BASE_PATH + fileName);
    }

    public static Integer getNumber(String instruction) {
        String number = instruction.substring(1);
        return Integer.valueOf(number);
    }
}