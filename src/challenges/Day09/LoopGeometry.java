package challenges.Day09;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class LoopGeometry {

    private final Map<Integer, List<Segment>> verticalEdgesByX;
    private final Map<Integer, List<Segment>> horizontalEdgesByY;
    private final int[] sortedVerticalEdgeXs;

    private final Map<Long, Boolean> tileContainmentCache = new HashMap<>();

    private LoopGeometry(Map<Integer, List<Segment>> verticalEdgesByX,
                         Map<Integer, List<Segment>> horizontalEdgesByY) {
        this.verticalEdgesByX = verticalEdgesByX;
        this.horizontalEdgesByY = horizontalEdgesByY;
        this.sortedVerticalEdgeXs = verticalEdgesByX.keySet().stream().mapToInt(x -> x).sorted().toArray();
    }

    static LoopGeometry fromRedTileLoop(int[] redXs, int[] redYs) {
        int numberOfRedTiles = redXs.length;

        Map<Integer, List<Segment>> verticalEdges = new HashMap<>();
        Map<Integer, List<Segment>> horizontalEdges = new HashMap<>();

        for (int current = 0; current < numberOfRedTiles; current++) {
            int next = (current + 1) % numberOfRedTiles;

            int startX = redXs[current];
            int startY = redYs[current];
            int endX = redXs[next];
            int endY = redYs[next];

            if (startX == endX) {
                verticalEdges
                        .computeIfAbsent(startX, k -> new ArrayList<>())
                        .add(Segment.between(startY, endY));
            } else {
                horizontalEdges
                        .computeIfAbsent(startY, k -> new ArrayList<>())
                        .add(Segment.between(startX, endX));
            }
        }

        return new LoopGeometry(verticalEdges, horizontalEdges);
    }

    boolean containsTile(int x, int y) {
        long cacheKey = packCoordinates(x, y);

        return tileContainmentCache.computeIfAbsent(
                cacheKey,
                key -> isOnBoundary(x, y) || isInsideByRayCasting(x, y)
        );
    }

    boolean rectangleFitsInsideLoop(int minX, int maxX, int minY, int maxY) {
        for (int edgeX : sortedVerticalEdgeXs) {
            if (edgeX <= minX || edgeX >= maxX) continue;

            for (Segment verticalSegment : verticalEdgesByX.get(edgeX)) {
                if (verticalSegment.overlapsOpen(minY, maxY)) {
                    return false;
                }
            }
        }

        for (Map.Entry<Integer, List<Segment>> entry : horizontalEdgesByY.entrySet()) {
            int edgeY = entry.getKey();
            if (edgeY <= minY || edgeY >= maxY) continue;

            for (Segment horizontalSegment : entry.getValue()) {
                if (horizontalSegment.overlapsOpen(minX, maxX)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isOnBoundary(int x, int y) {
        List<Segment> horizontalAtY = horizontalEdgesByY.get(y);
        if (horizontalAtY != null) {
            for (Segment segment : horizontalAtY) {
                if (segment.containsClosed(x)) return true;
            }
        }

        List<Segment> verticalAtX = verticalEdgesByX.get(x);
        if (verticalAtX != null) {
            for (Segment segment : verticalAtX) {
                if (segment.containsClosed(y)) return true;
            }
        }

        return false;
    }

    private boolean isInsideByRayCasting(int x, int y) {
        int crossings = 0;

        for (int edgeX : sortedVerticalEdgeXs) {
            if (edgeX <= x) continue;

            for (Segment segment : verticalEdgesByX.get(edgeX)) {
                if (y > segment.min && y <= segment.max) {
                    crossings++;
                }
            }
        }

        return (crossings & 1) == 1;
    }

    private long packCoordinates(int x, int y) {
        return ((long) x << 32) | (y & 0xFFFFFFFFL);
    }
}