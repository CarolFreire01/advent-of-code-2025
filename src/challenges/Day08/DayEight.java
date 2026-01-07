package challenges.Day08;

import java.util.Arrays;
import java.util.List;

import static util.Utils.getValueFile;

public class DayEight {

    private static final int CONNECTION = 1000;

    private long[] sortedPairData = null;

    public void dayEight() {
        List<String> lines = getValueFile("challenge-eight.txt");
        Playground playground = parseCords(lines);

        System.out.println("Part 1: " + largestCircuits(playground, CONNECTION));
        System.out.println("Part 2: " + finalConnectionCoordX(playground));

    }

    private Playground parseCords(List<String> lines) {
        var filter = lines.stream().filter(line -> !line.isBlank()).toList();

        int size = filter.size();
        int[] xcords = new int[size];
        int[] ycords = new int[size];
        int[] zcords = new int[size];

        for (int i = 0; i < size; i++) {
            var parts = filter.get(i).split(",");
            xcords[i] = Integer.parseInt(parts[0].trim());
            ycords[i] = Integer.parseInt(parts[1].trim());
            zcords[i] = Integer.parseInt(parts[2].trim());
        }

        return new Playground(xcords, ycords, zcords);
    }

    private long[] getSortedPairData(Playground playground) {
        if (this.sortedPairData == null) {
            int[] xCoord = playground.xcords();
            int[] yCoord = playground.ycords();
            int[] zCoord = playground.zcords();

            int n = xCoord.length;
            int pairCount = n * (n - 1) / 2;

            sortedPairData = new long[pairCount];
            int idx = 0;

            for (int i = 0; i < n; i++) {
                int xi = xCoord[i];
                int yi = yCoord[i];
                int zi = zCoord[i];

                for (int j = i + 1; j < n; j++) {
                    long dx = (long) xi - xCoord[j];
                    long dy = (long) yi - yCoord[j];
                    long dz = (long) zi - zCoord[j];

                    long seq = dx * dx + dy * dy + dz * dz;

                    sortedPairData[idx] = (seq << 20) | ((long) i << 10) | j;
                    idx++;
                }
            }

            Arrays.parallelSort(sortedPairData);
        }

        return sortedPairData;
    }

    private long largestCircuits(Playground playground, int connection) {
        var uf = new UnionFind(playground.xcords().length);
        var pairs = getSortedPairData(playground);

        int limit = Math.min(connection, pairs.length);
        for (int i = 0; i < limit; i++) {
            int a = extractBoxA(pairs[i]);
            int b = extractBoxB(pairs[i]);

            uf.union(a, b);
        }

        var sizes = uf.getComponentSizes();
        sizes.sort((a, b) -> Integer.compare(b, a));

        long product = 1;
        int count = Math.min(3, sizes.size());
        for (int i = 0; i < count; i++) {
            product *= sizes.get(i);
        }

        return product;
    }

    private static int extractBoxA(long packed) {
        return (int) ((packed >> 10) & 0x3FF);
    }

    private static int extractBoxB(long packed) {
        return (int) (packed & 0x3FF);
    }

    private long finalConnectionCoordX(Playground playground) {
        var uf = new UnionFind(playground.xcords().length);
        var pairs = getSortedPairData(playground);

        int[] xCoord = playground.xcords();

        int lastA = -1;
        int lastB = -1;

        for (long pair : pairs) {
            int a = extractBoxA(pair);
            int b = extractBoxB(pair);

            if (uf.find(a) != uf.find(b)) {
                uf.union(a, b);
                lastA = a;
                lastB = b;

                if (uf.isFullyConnected()) {
                    break;
                }
            }
        }

        if (lastA < 0) {
            return 0;
        }

        return (long) xCoord[lastA] * xCoord[lastB];
    }
}
