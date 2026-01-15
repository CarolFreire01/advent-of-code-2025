package challenges.Day09;

import static util.Utils.readMovieTheater;

public class DayNine {

    record Grid(boolean[][] isRedOrGreen, int minX, int minY, int pad) {}
    public record MovieTheater(int[] xcoords, int[] ycoords) {};

    public void dayNine() {
        MovieTheater movieTheater = readMovieTheater("challenge-nine.txt");
        System.out.println("Part One: " + getLargestRectangle(movieTheater));
        System.out.println("Part Two: " + validRectangule(movieTheater));

    }

    private long getLargestRectangle(MovieTheater theater) {
        int totalRedTiles = theater.xcoords().length;
        long largestArea = 0;

        for (int firstCornerIndex = 0; firstCornerIndex < totalRedTiles; firstCornerIndex++) {
            for (int secondCornerIndex = firstCornerIndex + 1; secondCornerIndex < totalRedTiles; secondCornerIndex++) {

                long rectangleArea =
                        computeAreaBetweenCorners(
                                theater.xcoords()[firstCornerIndex],
                                theater.ycoords()[firstCornerIndex],
                                theater.xcoords()[secondCornerIndex],
                                theater.ycoords()[secondCornerIndex]
                        );

                if (rectangleArea > largestArea) {
                    largestArea = rectangleArea;
                }
            }
        }

        return largestArea;

    }

    // ======================== PART TWO ========================
    private long validRectangule(MovieTheater movieTheater) {
        LoopGeometry loopGeometry = LoopGeometry.fromRedTileLoop(movieTheater.xcoords, movieTheater.ycoords);
        return findLargestRectangleValid(movieTheater, loopGeometry);
    }

    private long findLargestRectangleValid(MovieTheater theater, LoopGeometry loopGeometry) {
        int totalRedTiles = theater.xcoords().length;
        long largestValidArea = 0;

        for (int firstCorner = 0; firstCorner < totalRedTiles; firstCorner++) {
            int firstX = theater.xcoords()[firstCorner];
            int firstY = theater.ycoords()[firstCorner];

            for (int secondCorner = firstCorner + 1; secondCorner < totalRedTiles; secondCorner++) {
                int secondX = theater.xcoords()[secondCorner];
                int secondY = theater.ycoords()[secondCorner];

                long validArea = computeValidRectanguleArea(firstX, firstY, secondX, secondY, loopGeometry);

                if (validArea > largestValidArea) {
                    largestValidArea = validArea;
                }
            }
        }

        return largestValidArea;
    }

    private long computeValidRectanguleArea(int cornerAX, int cornerAY,
                                            int cornerBX, int cornerBY,
                                            LoopGeometry loopGeometry) {

        if (cornerAX == cornerBX || cornerAY == cornerBY) return 0;

        if (!loopGeometry.containsTile(cornerAX, cornerBY)) return 0;
        if (!loopGeometry.containsTile(cornerBX, cornerAY)) return 0;

        int minX = Math.min(cornerAX, cornerBX);
        int maxX = Math.max(cornerAX, cornerBX);
        int minY = Math.min(cornerAY, cornerBY);
        int maxY = Math.max(cornerAY, cornerBY);

        if (!loopGeometry.rectangleFitsInsideLoop(minX, maxX, minY, maxY)) return 0;

        return computeAreaBetweenCorners(cornerAX, cornerAY, cornerBX, cornerBY);
    }

    private long computeAreaBetweenCorners(int firstX, int firstY, int secondX, int secondY) {
        long width = Math.abs((long) secondX - firstX) + 1;
        long height = Math.abs((long) secondY - firstY) + 1;
        return width * height;
    }

}
