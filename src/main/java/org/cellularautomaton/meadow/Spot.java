package org.cellularautomaton.meadow;

import org.cellularautomaton.bee.Bee;
import org.cellularautomaton.plant.Flower;
import java.util.ArrayList;


public final class Spot {
    private final int maxSpotCapacity;

    private Object spotOccupancy;

    private ArrayList<Flower> spotSeedsOn;

    private ArrayList<Bee> spotBeesOn;
    private final int x;
    private final int y;

    public Spot(int x, int y) {
        this.x = x;
        this.y = y;
        this.maxSpotCapacity = 10;
        this.spotSeedsOn = new ArrayList<>();
        this.spotBeesOn = new ArrayList<>();
    }

    public Object getSpotOccupancy() {
        return this.spotOccupancy;
    }

    public void setSpotOccupancy(Object object) {
        this.spotOccupancy = object;
    }

    public ArrayList<Flower> getSpotSeedsOn() {
        return this.spotSeedsOn;
    }

    public void setSpotSeedsOn(ArrayList<Flower> seeds) {
        this.spotSeedsOn = seeds;
    }

    public ArrayList<Bee> getSpotBeesOn() {
        return this.spotBeesOn;
    }

    public void setSpotBeesOn(ArrayList<Bee> bees) {
        this.spotBeesOn = bees;
    }

    // Прорастет ли семя на клетке поля
    public Boolean willSeedGerminate(Flower flowerType) {
        double probOfGerm = spotSeedsOn.stream().filter(seed -> seed.getClass().getSimpleName().equals(flowerType.getClass().getSimpleName())).count() / (spotSeedsOn.size() + 0.000001);
        return (probOfGerm > (1.0 - probOfGerm)) && (spotOccupancy == null);
    }

    public boolean checkSpotAvailability() {
        return this.spotSeedsOn.size() <= this.maxSpotCapacity && this.spotOccupancy == null;
    }

    public String getPosition() {
        return "x=" + this.x + " y=" + this.y;
    }

    public int getXCoordinate() {
        return this.x;
    }

    public int getYCoordinate() {
        return this.y;
    }

    public String getSpotObject() {
        if (this.spotOccupancy instanceof Flower) {
            return "Flower";
        } else {
            return "";
        }
    }

}
