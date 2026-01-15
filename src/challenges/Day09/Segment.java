package challenges.Day09;

final class Segment {
    final int min;
    final int max;

    private Segment(int min, int max) {
        this.min = min;
        this.max = max;
    }

    static Segment between(int a, int b) {
        return new Segment(Math.min(a, b), Math.max(a, b));
    }

    boolean containsClosed(int v) {
        return v >= min && v <= max;
    }

    /**
     * Overlap with (otherMin, otherMax) interior only.
     * (Touching at border is not considered crossing.)
     */
    boolean overlapsOpen(int otherMin, int otherMax) {
        return min < otherMax && max > otherMin;
    }
}
