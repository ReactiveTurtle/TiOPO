package ru.reactiveturtle;

public class Main {
    public static void main(String[] args) {
        try {
            double[] sides = SidesParser.parseSidesOrThrow(args);
            TriangleType triangleType = TriangleHelper.detectType(sides[0], sides[1], sides[2]);
            System.out.println(TriangleHelper.toRussianString(triangleType));
        } catch (ValidationException e) {
            System.out.println("Неизвестная ошибка");
        }
    }
}
