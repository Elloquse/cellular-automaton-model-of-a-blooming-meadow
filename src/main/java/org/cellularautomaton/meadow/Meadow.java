package org.cellularautomaton.meadow;

import org.cellularautomaton.bee.AllHives;
import org.cellularautomaton.plant.AllFlowers;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public final class Meadow {
    private final AllSpots spotsObject;
    private final int xSize;
    private final int ySize;

    public Meadow(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.spotsObject = new AllSpots(this.xSize, this.ySize);
    }

    public void initialize(List<List<Integer>> hives, HashMap<List<Integer>, String> flowers) throws Throwable {
        this.spotsObject.addFlowers(flowers);
        this.spotsObject.addHives(hives);
    }

    public void runNewIteration() {
        AllFlowers.tick();
        AllHives.tick();
    }

    // Вывод статистики о состоянии поля
    public void showStatistics(int time, String pathToSave) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(pathToSave + "simulation_results_" + time +  "_days.txt");
        out.println("Day: " + time);
        out.println();
        for (String flower : AllFlowers.getStatistics()) {
            out.println(flower);
        }
        for (String hive : AllHives.getStatistics()) {
            out.println(hive);
        }
        out.close();
    }

    public List<Integer> getMeadowSize() {
        return Stream.of(this.xSize, this.ySize).collect(Collectors.toList());
    }
}

