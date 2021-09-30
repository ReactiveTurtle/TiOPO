package ru.reactiveturtle;

public final class SidesParser {
    public SidesParser() {
    }

    public static double[] parseSidesOrThrow(String[] args) throws ValidationException {
        if (args.length != 3) {
            throw new ValidationException("Argument's count must be equal 3");
        }
        double[] sides = new double[3];
        try {
            sides[0] = Double.parseDouble(args[0]);
            sides[1] = Double.parseDouble(args[1]);
            sides[2] = Double.parseDouble(args[2]);
        } catch (NumberFormatException exception) {
            throw new ValidationException("One of the sides is not a number");
        }
        if (sides[0] <= 0 || sides[0] > Integer.MAX_VALUE) {
            throw new ValidationException("First side length must be positive. Now is " + sides[0]);
        }
        if (sides[1] <= 0 || sides[1] > Integer.MAX_VALUE) {
            throw new ValidationException("Second side length must be positive. Now is " + sides[1]);
        }
        if (sides[2] <= 0 || sides[2] > Integer.MAX_VALUE) {
            throw new ValidationException("Third side length must be positive. Now is " + sides[2]);
        }
        return sides;
    }
}
