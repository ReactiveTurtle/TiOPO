package ru.reactiveturtle.tiopo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class CarTests {
    @Test
    public void CreateCar_Created() {
        // Act
        Car car = new Car();

        // Assert
        Assertions.assertEquals(Gear.NEUTRAL, car.getGear());
        Assertions.assertEquals(0, car.getSpeed());
        Assertions.assertFalse(car.isTurnedOn());
    }

    @Test
    public void TurnOnEngine_IsEngineOff_TurnOn() {
        // Arrange
        Car car = new Car();

        // Act
        boolean isTurnedOn = car.turnOnEngine();

        // Assert
        Assertions.assertTrue(isTurnedOn);
        Assertions.assertEquals(Gear.NEUTRAL, car.getGear());
        Assertions.assertEquals(0, car.getSpeed());
        Assertions.assertTrue(car.isTurnedOn());
    }

    @Test
    public void TurnOnEngine_IsEngineOn_StaysOn() {
        // Arrange
        Car car = new Car();
        car.turnOnEngine();

        // Act
        boolean isTurnedOn = car.turnOnEngine();

        // Assert
        Assertions.assertTrue(isTurnedOn);
        Assertions.assertEquals(Gear.NEUTRAL, car.getGear());
        Assertions.assertEquals(0, car.getSpeed());
        Assertions.assertTrue(car.isTurnedOn());
    }

    @Test
    public void TurnOffEngine_IsEngineOff_StaysOff() {
        // Arrange
        Car car = new Car();

        // Act
        boolean isTurnedOff = car.turnOffEngine();

        // Assert
        Assertions.assertTrue(isTurnedOff);
        Assertions.assertEquals(Gear.NEUTRAL, car.getGear());
        Assertions.assertEquals(0, car.getSpeed());
        Assertions.assertFalse(car.isTurnedOn());
    }

    @Test
    public void TurnOffEngine_IsEngineOn_TurnOff() {
        // Arrange
        Car car = new Car();
        car.turnOnEngine();

        // Act
        boolean isTurnedOff = car.turnOffEngine();

        // Assert
        Assertions.assertTrue(isTurnedOff);
        Assertions.assertEquals(Gear.NEUTRAL, car.getGear());
        Assertions.assertEquals(0, car.getSpeed());
        Assertions.assertFalse(car.isTurnedOn());
    }

    @ParameterizedTest
    @EnumSource(
            value = Gear.class,
            names = {
                    "BACK",
                    "NEUTRAL",
                    "FIRST",
                    "SECOND",
                    "THIRD",
                    "FOURTH",
                    "FIFTH"})
    public void SetGear_IsEngineOff_GearNotSet(Gear gear) {
        // Arrange
        Car car = new Car();

        // Act
        boolean isSet = car.setGear(gear);

        // Assert
        Assertions.assertFalse(isSet);
        Assertions.assertEquals(Gear.NEUTRAL, car.getGear());
        Assertions.assertEquals(0, car.getSpeed());
        Assertions.assertFalse(car.isTurnedOn());
    }

    @Test
    public void TurnOffEngine_EngineIsOnAndBackGear_NotTurnOff() {
        // Arrange
        Car car = new Car();
        car.turnOnEngine();
        car.setGear(Gear.BACK);

        // Act
        boolean isTurnedOff = car.turnOffEngine();

        // Assert
        Assertions.assertFalse(isTurnedOff);
        Assertions.assertEquals(Gear.BACK, car.getGear());
        Assertions.assertEquals(0, car.getSpeed());
        Assertions.assertTrue(car.isTurnedOn());
    }

    @Test
    public void SetFirstGear_EngineIsOnAndBackGearAndSpeedIsZero_Set() {
        // Arrange
        Car car = new Car();
        car.turnOnEngine();
        car.setGear(Gear.BACK);

        // Act
        boolean isSetFirstGear = car.setGear(Gear.FIRST);

        // Assert
        Assertions.assertTrue(isSetFirstGear);
        Assertions.assertEquals(Gear.FIRST, car.getGear());
        Assertions.assertEquals(0, car.getSpeed());
        Assertions.assertTrue(car.isTurnedOn());
    }

    @Test
    public void SetBackGear_EngineIsOnAndNeutralGearAndSpeedIs10_NotSet() {
        // Arrange
        Car car = new Car();
        car.turnOnEngine();
        car.setGear(Gear.FIRST);
        car.setSpeed(10);
        car.setGear(Gear.NEUTRAL);

        // Act
        boolean isSetGear = car.setGear(Gear.BACK);

        // Assert
        Assertions.assertFalse(isSetGear);
        Assertions.assertEquals(Gear.NEUTRAL, car.getGear());
        Assertions.assertEquals(10, car.getSpeed());
        Assertions.assertTrue(car.isTurnedOn());
    }

    @Test
    public void SetBackGear_EngineIsOnAndFirstGearAndSpeedIs10_NotSet() {
        // Arrange
        Car car = new Car();
        car.turnOnEngine();
        car.setGear(Gear.FIRST);
        car.setSpeed(10);

        // Act
        boolean isSetBackGear = car.setGear(Gear.BACK);

        // Assert
        Assertions.assertFalse(isSetBackGear);
        Assertions.assertEquals(Gear.FIRST, car.getGear());
        Assertions.assertEquals(10, car.getSpeed());
        Assertions.assertTrue(car.isTurnedOn());
    }

    @Test
    public void SetSecondGear_EngineIsOnAndNeutralGear_NotSet() {
        // Arrange
        Car car = new Car();
        car.turnOnEngine();

        // Act
        boolean isSetGear = car.setGear(Gear.SECOND);

        // Assert
        Assertions.assertFalse(isSetGear);
        Assertions.assertEquals(Gear.NEUTRAL, car.getGear());
        Assertions.assertEquals(0, car.getSpeed());
        Assertions.assertTrue(car.isTurnedOn());
    }

    @Test
    public void SetSpeed100_EngineIsOff_NotSet() {
        // Arrange
        Car car = new Car();

        // Act
        boolean isSetSpeed = car.setSpeed(100);

        // Assert
        Assertions.assertFalse(isSetSpeed);
        Assertions.assertEquals(Gear.NEUTRAL, car.getGear());
        Assertions.assertEquals(0, car.getSpeed());
        Assertions.assertFalse(car.isTurnedOn());
    }

    @Test
    public void SetSpeed100_EngineIsOnAndFirstGear_NotSet() {
        // Arrange
        Car car = new Car();
        car.turnOnEngine();
        car.setGear(Gear.FIRST);

        // Act
        boolean isSetSpeed = car.setSpeed(100);

        // Assert
        Assertions.assertFalse(isSetSpeed);
        Assertions.assertEquals(Gear.FIRST, car.getGear());
        Assertions.assertEquals(0, car.getSpeed());
        Assertions.assertTrue(car.isTurnedOn());
    }

    @Test
    public void SetSpeed5_EngineIsOnAndNeutralGearAndSpeed10_NotSet() {
        // Arrange
        Car car = new Car();
        car.turnOnEngine();
        car.setGear(Gear.FIRST);
        car.setSpeed(10);
        car.setGear(Gear.NEUTRAL);

        // Act
        boolean isSetSpeed = car.setSpeed(5);

        // Assert
        Assertions.assertTrue(isSetSpeed);
        Assertions.assertEquals(Gear.NEUTRAL, car.getGear());
        Assertions.assertEquals(5, car.getSpeed());
        Assertions.assertTrue(car.isTurnedOn());
    }

    @Test
    public void SetSpeedMinus5_EngineIsOnAndBackGearAndSpeed0_NotSet() {
        // Arrange
        Car car = new Car();
        car.turnOnEngine();
        car.setGear(Gear.BACK);

        // Act
        boolean isSetSpeed = car.setSpeed(-5);

        // Assert
        Assertions.assertFalse(isSetSpeed);
        Assertions.assertEquals(Gear.BACK, car.getGear());
        Assertions.assertEquals(0, car.getSpeed());
        Assertions.assertTrue(car.isTurnedOn());
    }
}
