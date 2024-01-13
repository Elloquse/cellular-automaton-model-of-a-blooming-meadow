package org.cellularautomaton.bee;

import org.cellularautomaton.meadow.AllSpots;
import org.cellularautomaton.meadow.Spot;
import org.cellularautomaton.plant.Flower;

import java.util.NoSuchElementException;
import java.util.Random;

public final class Bee {
    private Spot beeSpot;
    private final Hive beeHive;
    private final int nectarLoadCapacity;
    private final int maxFlights;
    private int currentFlights;
    private final int maxFlightDistance;
    private int currentFlightedDistance;
    private int currentNectar;
    private int stepsToReturn;
    private int currentStepInReturning;
    private Object caughtPollen;


    public Bee(Hive hive) {
        this.beeHive = hive;
        this.nectarLoadCapacity = 5;
        this.maxFlights = 20;
        this.maxFlightDistance = 7;
        this.beeSpot = hive.getHiveSpot();
        getBeeSpot().getSpotBeesOn().add(this);
    }

    public void setBeeSpot(Spot obj) {
        this.beeSpot = obj;
    }

    public Spot getBeeSpot() {
        return this.beeSpot;
    }

    // Передвижение пчелы при итерации
    public void move() {
        if (this.currentFlightedDistance == this.maxFlightDistance) {
            this.currentFlights += 1;
            if (this.currentFlights == this.maxFlights) {
                this.die();
                return;
            }
            this.returnToHive();
        } else {
            // Перемещение пчелы в случайную свободную клетку в радиусе одной клетки
            int x = this.beeSpot.getXCoordinate();
            int y = this.beeSpot.getYCoordinate();
            Random r = new Random();
            try {
                int xRandom = r.nextInt((x + 1) - (x - 1) + 1) + (x - 1);
                int yRandom = r.nextInt((y + 1) - (y - 1) + 1) + (y - 1);
                Spot spotToBeeOn = AllSpots.getSpotObject(xRandom, yRandom);
                if (spotToBeeOn != null) {
                    getBeeSpot().getSpotBeesOn().remove(this);
                    setBeeSpot(spotToBeeOn);
                    getBeeSpot().getSpotBeesOn().add(this);
                    currentFlightedDistance += 1;
                }
            } catch (NoSuchElementException e) {
                move();
            }
        }
    }

    // Сбор нектара и пыльцы пчелой, если клетка занята цветком
    public void spotProcessing() {
        if (getBeeSpot().getSpotOccupancy() instanceof Flower) {
            catchPollen();
            currentNectar = ((Flower) getBeeSpot().getSpotOccupancy()).getNectar(currentNectar, nectarLoadCapacity);
        }
    }

    private void returnToHive() {
        if (stepsToReturn == 0) {
            int hiveX = beeHive.getHiveSpot().getXCoordinate();
            int hiveY = beeHive.getHiveSpot().getYCoordinate();
            int beeX = getBeeSpot().getXCoordinate();
            int beeY = getBeeSpot().getYCoordinate();

            stepsToReturn = Math.abs((hiveX + hiveY) - (beeX + beeY));
            getBeeSpot().getSpotBeesOn().remove(this);
            return;
        }

        if (currentStepInReturning == stepsToReturn) {
            setBeeSpot(beeHive.getHiveSpot());
            getBeeSpot().getSpotBeesOn().add(this);
            stepsToReturn = 0;
            giveNectarToHive();
            return;
        }
        
        currentStepInReturning += 1;
    }

    private void giveNectarToHive() {
        this.beeHive.takeNectar(this.currentNectar);
        this.currentNectar = 0;
    }

    private void die() {
        getBeeSpot().getSpotBeesOn().remove(this);
        beeHive.addDeadBee(this);
    }

    private void catchPollen() {
        if (caughtPollen == null) {
            caughtPollen = getBeeSpot().getSpotOccupancy();
        } else {
            if (((Flower) getBeeSpot().getSpotOccupancy()).getPlantState().toString().equals("BLOOMING") &&
                    caughtPollen.getClass().getSimpleName().equals(getBeeSpot().getSpotOccupancy().getClass().getSimpleName())) {
                ((Flower) getBeeSpot().getSpotOccupancy()).pollinate();
                caughtPollen = null;
            }
        }
    }
}

