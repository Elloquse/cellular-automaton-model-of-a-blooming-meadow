package org.cellularautomaton.bee;

import org.cellularautomaton.meadow.Spot;

import java.util.ArrayList;


public final class AllHives {
    private static final ArrayList<Hive> hivesList = new ArrayList<>();

    // Размещение улья на клетке
    public static void placeHive(Spot spot) {
        Hive newHive = new Hive(spot);
        newHive.getHiveSpot().setSpotOccupancy(newHive);
        hivesList.add(newHive);
    }

    public static void tick() {
        for (Hive hive : hivesList) {
            hive.tick();
        }
    }

    public int getTotalBees() {
        int totalBees = 0;

        for (Hive hive : hivesList) {
            totalBees += hive.getBees().size();
        }
        
        return totalBees;
    }

    public static ArrayList<String> getStatistics() {
        ArrayList<String> hiveStats = new ArrayList<>();
        hiveStats.add("-----Hives statistics-----");
        for (Hive hive : hivesList) {
             hiveStats.add("Hive position: " + hive.getHiveSpot().getPosition());
             hiveStats.add("     Bees alive: " + hive.getBeesList().size());
             hiveStats.add("     Bees dead: " + hive.getBeesDead());
             hiveStats.add("     Generations have past: " + hive.getBeesGeneration());
             hiveStats.add("     Total nectar collected: " + hive.getNectarTotal());
             hiveStats.add("     Total nectar spent: " + hive.getNectarSpent());
             hiveStats.add("     Current nectar: " + hive.getNectarCurrent());
             hiveStats.add("");
        }
         hiveStats.add("");
        return hiveStats;
    }
}

