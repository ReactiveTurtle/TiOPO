package ru.reactiveturtle.tiopo;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Car {
    private final static Map<Gear, Pair<Integer, Integer>> speedRanges = new HashMap<>();

    static {
        speedRanges.put(Gear.BACK, new Pair<>(0, 20));
        speedRanges.put(Gear.FIRST, new Pair<>(0, 30));
        speedRanges.put(Gear.SECOND, new Pair<>(20, 50));
        speedRanges.put(Gear.THIRD, new Pair<>(30, 60));
        speedRanges.put(Gear.FOURTH, new Pair<>(40, 90));
        speedRanges.put(Gear.FIFTH, new Pair<>(50, 150));
    }

    private boolean isTurnedOn;
    private Gear gear;
    private float speed;

    public Car() {
        gear = Gear.NEUTRAL;
        speed = 0;
        isTurnedOn = false;
    }

    public boolean turnOnEngine() {
        isTurnedOn = true;
        return true;
    }

    public boolean turnOffEngine() {
        if (gear == Gear.NEUTRAL && speed == 0) {
            isTurnedOn = false;
            return true;
        }
        return false;
    }

    public boolean setGear(Gear gear) {
        Objects.requireNonNull(gear);
        if (!isTurnedOn) {
            return false;
        }
        Pair<Integer, Integer> speedRange = speedRanges.get(gear);
        if (gear == Gear.BACK) {
            if (this.gear != Gear.NEUTRAL) {
                return false;
            } else if (this.speed != 0) {
                return false;
            }
        }
        if (gear == Gear.NEUTRAL) {
            this.gear = gear;
            return true;
        } else if (gear == Gear.FIRST && this.gear == Gear.BACK && this.speed == 0) {
            this.gear = gear;
            return true;
        } else if (speedRange.getKey() <= speed && speed <= speedRange.getValue()) {
            this.gear = gear;
            return true;
        }
        return false;
    }

    public boolean setSpeed(float speed) {
        if (!isTurnedOn || speed < 0) {
            return false;
        }
        Pair<Integer, Integer> speedRange = speedRanges.get(gear);
        if (gear == Gear.NEUTRAL && speed < this.speed) {
            this.speed = speed;
            return true;
        } else if (speedRange.getKey() <= speed && speed <= speedRange.getValue()) {
            this.speed = speed;
            return true;
        }
        return false;
    }

    public boolean isTurnedOn() {
        return isTurnedOn;
    }

    public Gear getGear() {
        return gear;
    }

    public float getSpeed() {
        return speed;
    }
}
