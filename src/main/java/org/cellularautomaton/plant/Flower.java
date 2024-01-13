package org.cellularautomaton.plant;

import org.cellularautomaton.Tickable;
import org.cellularautomaton.meadow.AllSpots;
import org.cellularautomaton.meadow.Spot;
import java.util.Random;


public abstract class Flower implements Tickable {

    private Spot plantSpot;
    private int plantAge = 0;
    private int plantNectar;
    private boolean isPollinated = false;


    public final Spot getPlantSpot() {
        return this.plantSpot;
    }

    public final void setPlantSpot(Spot spot) {
        this.plantSpot = spot;
    }

    public abstract Enum getPlantState();


    public abstract void setPlantState(Enum newPlantState);

    public void germinate() {
        plantSpot.setSpotOccupancy(this);

        this.setPlantState(PlantState.changeState(this, "SEEDLING"));
        AllFlowers.removeSeeds(plantSpot.getSpotSeedsOn());
        plantSpot.getSpotSeedsOn().removeAll(plantSpot.getSpotSeedsOn());
        AllFlowers.addPlant(this);
    }

    private void growAdult() {
        this.setPlantState(PlantState.changeState(this, "ADULT"));
    }

    private void bloom() {
        this.setPlantState(PlantState.changeState(this, "BLOOMING"));
    }

    private void produceNectar() {
        Random r = new Random();
        this.plantNectar =  r.nextInt((1000-500) + 1) + (500);
    }

    private void fruit() {
        this.plantNectar = 0;
        this.setPlantState(PlantState.changeState(this, "FRUITION"));
    }

    private void die() {
        getPlantSpot().setSpotOccupancy(null);
        this.setPlantSpot(null);
        this.setPlantState(PlantState.changeState(this, "DEAD"));
    }

    private void produceSeed() {
        int x = plantSpot.getXCoordinate();
        int y = plantSpot.getYCoordinate();
        Random r = new Random();
        for (int cell = 20; cell <= r.nextInt(25-20 + 1) + 20; cell++) {
            int xRandom =  r.nextInt((x+1)-(x-1) + 1) + (x-1);
            int yRandom =  r.nextInt((y+1)-(y-1) + 1) + (y-1);
            Spot spotToSeedOn = AllSpots.getSpotObject(xRandom, yRandom);
            if (spotToSeedOn != null) {
                if (spotToSeedOn.checkSpotAvailability()) {
                    AllFlowers.placeFlower(spotToSeedOn, this.getClass().getSimpleName());
                }
            }
        }
    }

    public final int getNectar(int currentBeeNectar, int nectarLoadCapacity) {
        int nectarToReturn = nectarLoadCapacity - currentBeeNectar;
        if (this.plantNectar >= nectarToReturn) {
            this.plantNectar -= nectarToReturn;
            return nectarToReturn;
        } else {
            return 0;
        }
    }

    public final void pollinate() {
        this.isPollinated = true;
    }

    @Override
    public void tick() {
        switch (this.getPlantState().toString()) {
            case ("SEED"):
                if (plantAge > PlantState.getLifeTime(this)) {
                    Boolean germinateOrNot = plantSpot.willSeedGerminate(this);
                    if (germinateOrNot) {
                        plantAge = 1;
                        germinate();
                    } else {
                        die();
                    }
                } else {
                    plantAge += 1;
                }
            break;
            case ("SEEDLING"):
                if (plantAge > PlantState.getLifeTime(this)) {
                    plantAge = 1;
                    growAdult();
                } else {
                    plantAge += 1;
                }
            break;
            case ("ADULT"):
                if (plantAge > PlantState.getLifeTime(this)) {
                    plantAge = 1;
                    bloom();
                } else {
                    plantAge += 1;
                }
            break;
            case ("BLOOMING"):
                if (plantAge > PlantState.getLifeTime(this) && isPollinated) {
                    plantAge = 1;
                    fruit();
                } else if (plantAge > PlantState.getLifeTime(this) && !isPollinated) {
                    die();
                } else {
                    plantAge += 1;
                    produceNectar();
                }
            break;
            case ("FRUITION"):
                if (plantAge > PlantState.getLifeTime(this)) {
                    die();
                } else {
                    plantAge += 1;
                    produceSeed();
                }
            break;
        }
    }
}
