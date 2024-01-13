package org.cellularautomaton.bee;

import org.cellularautomaton.Tickable;
import org.cellularautomaton.meadow.Spot;
import java.util.ArrayList;
import java.util.Random;

public final class Hive implements Tickable {

    private Spot hiveSpot;

    private ArrayList<Bee> beesList;
    private int beesDead;

    private ArrayList<Bee> listOfCurrentDeadBees;
    private int beesGeneration;
    private int nectarCurrent;
    private int nectarTotal;
    private int nectarSpent;
    private final int nectarToNewGeneration;
    private final int maxBeesCapacity;

    // Размещение улья с пчелами
    public Hive(Spot spot) {
        this.hiveSpot = spot;
        Random r = new Random();
        int beesAlive = r.nextInt((500 - 400) + 1) + (400);

        this.beesList = new ArrayList<>();
        for (int bee = 1; bee <= beesAlive; bee++) {
            beesList.add(new Bee(this));
        }

        this.listOfCurrentDeadBees = new ArrayList<>();
        this.beesGeneration = 1;
        this.nectarToNewGeneration = 10;
        this.maxBeesCapacity = 500;
    }

    public Spot getHiveSpot() {
        return this.hiveSpot;
    }

    public void setHiveSpot(Spot obj) {
        this.hiveSpot = obj;
    }


    public ArrayList<Bee> getBeesList() {
        return this.beesList;
    }

    public void setBeesList(ArrayList<Bee> bees) { this.beesList = bees; }

    public int getBeesDead() {
        return this.beesDead;
    }

    public void setBeesDead(int bees) {
        this.beesDead = bees;
    }

    public ArrayList<Bee> getListOfCurrentDeadBees() {
        return this.listOfCurrentDeadBees;
    }

    public void setListOfCurrentDeadBees(ArrayList<Bee> bees) { this.listOfCurrentDeadBees = bees; }

    public int getBeesGeneration() {
        return this.beesGeneration;
    }

    public void setBeesGeneration(int generation) {
        this.beesGeneration = generation;
    }

    public int getNectarCurrent() {
        return this.nectarCurrent;
    }

    public void setNectarCurrent(int nectar) {
        this.nectarCurrent = nectar;
    }

    public int getNectarTotal() {
        return this.nectarTotal;
    }

    public void setNectarTotal(int nectar) { this.nectarTotal = nectar; }

    public int getNectarSpent() {
        return this.nectarSpent;
    }

    public void setNectarSpent(int nectar) {
        this.nectarSpent = nectar;
    }

    private void createNewGeneration() {
        Random r = new Random();
        int maxBees =  r.nextInt((70-40) + 1) + (40);
        for (int newBee=40; newBee<=maxBees; newBee++) {
            if (beesList.size() <= maxBeesCapacity) {
                beesList.add(new Bee(this));
            }
        }
    }

    public ArrayList<Bee> getBees() { return this.beesList; }

    @Override
    public void tick() {
        ArrayList<Bee> currentBeesList = new ArrayList<>(this.beesList);
        for (Bee bee : currentBeesList) {
            bee.move();
            bee.spotProcessing();
        }
        if (this.nectarCurrent >= this.nectarToNewGeneration) {
            this.createNewGeneration();
            this.nectarCurrent -= this.nectarToNewGeneration;
            this.nectarSpent += this.nectarToNewGeneration;
            ++this.beesGeneration;
        }
        this.beesList.removeAll(this.listOfCurrentDeadBees);
    }

    public void takeNectar(int nectarByBee) {
        this.nectarCurrent += nectarByBee;
        this.nectarTotal += nectarByBee;
    }

    public void addDeadBee(Bee bee) {
        this.beesDead += 1;
        this.listOfCurrentDeadBees.add(bee);
    }
}

