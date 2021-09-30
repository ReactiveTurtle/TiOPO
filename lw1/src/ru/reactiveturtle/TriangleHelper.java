package ru.reactiveturtle;

import java.util.Arrays;

public final class TriangleHelper {
    private TriangleHelper() {
    }

    public static TriangleType detectType(double firstSide,
                                          double secondSide,
                                          double thirdSide) {
        double maxSide;
        double middleSide;
        double minSide;
        double[] sides = new double[]{firstSide, secondSide, thirdSide};
        Arrays.sort(sides);
        maxSide = sides[2];
        middleSide = sides[1];
        minSide = sides[0];

        if (minSide + middleSide <= maxSide) {
            return TriangleType.NOT_TRIANGLE;
        }
        if (minSide == middleSide && middleSide == maxSide) {
            return TriangleType.EQUILATERAL;
        }
        if (minSide == middleSide || middleSide == maxSide) {
            return TriangleType.ISOSCELES;
        }
        return TriangleType.USUAL;
    }

    public static String toRussianString(TriangleType triangleType) {
        switch (triangleType) {
            case USUAL:
                return "Обычный";
            case ISOSCELES:
                return "Равнобедренный";
            case EQUILATERAL:
                return "Равносторонний";
            case NOT_TRIANGLE:
                return "Не треугольник";
            default:
                throw new EnumConstantNotPresentException(TriangleType.class, "triangleType");
        }
    }
}
