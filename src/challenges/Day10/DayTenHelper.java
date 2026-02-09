package challenges.Day10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static util.Utils.getInputStream;

public class DayTenHelper {

    private static final String BASE_PATH = "files/";

    private static final Pattern BRACKETS = Pattern.compile("\\[(.*?)]");
    private static final Pattern PARENS = Pattern.compile("\\(([^)]*)\\)");
    private static final Pattern CURLY = Pattern.compile("\\{(.*?)}");

    public static List<Machine> readMachinesFromResource(String fileName) throws IOException {
        InputStream is = getInputStream(fileName);
        if (is == null) {
            throw new IllegalArgumentException("Resource not found: " + BASE_PATH + fileName);
        }

        List<Machine> machines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String raw;
            int lineNumber = 0;
            while ((raw = br.readLine()) != null) {
                lineNumber++;
                String line = raw.trim();
                if (line.isEmpty() || line.startsWith("//")) continue;

                try {
                    machines.add(parseMachineLine(line));
                } catch (RuntimeException ex) {
                    throw new IllegalArgumentException("Error parsing line " + lineNumber + ": " + raw, ex);
                }
            }
        }

        return machines;
    }

    public static Machine parseMachineLine(String line) {
        int[] indicator = parseIndicator(extractSingle(line, BRACKETS, "indicator [ ... ]"));
        List<int[]> buttons = parseButtons(line);
        int[] joltage = parseIntList(extractSingle(line, CURLY, "joltage { ... }"));

        validate(indicator, buttons);

        return new Machine(indicator, buttons, joltage);
    }

    private static int[] parseIndicator(String insideBrackets) {
        int n = insideBrackets.length();
        int[] out = new int[n];
        for (int i = 0; i < n; i++) {
            char c = insideBrackets.charAt(i);
            if (c == '.') out[i] = 0;
            else if (c == '#') out[i] = 1;
            else throw new IllegalArgumentException("Invalid indicator char: '" + c + "'");
        }
        return out;
    }

    private static List<int[]> parseButtons(String line) {
        List<int[]> buttons = new ArrayList<>();
        Matcher m = PARENS.matcher(line);

        while (m.find()) {
            String inside = m.group(1).trim();

            if (inside.isEmpty()) {
                throw new IllegalArgumentException("Empty button: ()");
            }

            buttons.add(parseIntList(inside));
        }

        if (buttons.isEmpty()) {
            throw new IllegalArgumentException("No buttons found");
        }

        return buttons;
    }

    private static int[] parseIntList(String commaSeparated) {
        String[] parts = commaSeparated.split(",");
        int[] out = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            String s = parts[i].trim();
            if (s.isEmpty()) throw new IllegalArgumentException("Empty integer token in: " + commaSeparated);
            out[i] = Integer.parseInt(s);
        }
        return out;
    }

    private static String extractSingle(String line, Pattern pattern, String label) {
        Matcher m = pattern.matcher(line);
        if (!m.find()) {
            throw new IllegalArgumentException("Missing " + label + " in line: " + line);
        }
        return m.group(1);
    }

    private static void validate(int[] indicator, List<int[]> buttons) {
        int n = indicator.length;
        for (int b = 0; b < buttons.size(); b++) {
            int[] btn = buttons.get(b);
            for (int idx : btn) {
                if (idx < 0 || idx >= n) {
                    throw new IllegalArgumentException(
                            "Button " + b + " references out-of-range light index " + idx +
                                    " for indicator size " + n
                    );
                }
            }
        }
    }

    /// PRETTY FORMATTER
    public static int[] toButtonMasks(List<int[]> buttons) {
        int[] masks = new int[buttons.size()];

        for (int i = 0; i < buttons.size(); i++) {
            int mask = 0;
            for (int idx : buttons.get(i)) {
                mask |= (1 << idx);
            }
            masks[i] = mask;
        }

        return masks;
    }

    public static int toMask(int[] bits) {
        int mask = 0;
        for (int i = 0; i < bits.length; i++) {
            if (bits[i] == 1) {
                mask |= (1 << i);
            }
        }
        return mask;
    }


}
