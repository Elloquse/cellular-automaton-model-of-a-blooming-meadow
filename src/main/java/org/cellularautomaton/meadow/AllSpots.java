package org.cellularautomaton.meadow;

import org.cellularautomaton.bee.AllHives;
import org.cellularautomaton.plant.AllFlowers;
import org.cellularautomaton.plant.Chamomile;
import org.cellularautomaton.plant.Dandelion;
import org.cellularautomaton.plant.Flower;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class AllSpots {

    private static final HashMap<List<Integer>, Spot> spotsMap = new HashMap<>();

    public AllSpots (int meadowSizeX, int meadowSizeY) {
        for (int line=1; line<=meadowSizeY; line++) {
            for (int column=1; column<=meadowSizeX; column++) {
                spotsMap.put(Stream.of(line, column).collect(Collectors.toList()), new Spot(column, line));
            }
        }
    }

    public void addHives(List<List<Integer>> hives) {
        for (List<Integer> hive : hives) {
            AllHives.placeHive(spotsMap.get(hive));
        }
    }

    public void addFlowers(Map<List<Integer>, String> flowers) {
        for (Map.Entry<List<Integer>, String> flower : flowers.entrySet()) {
            AllFlowers.placeFlower(spotsMap.get(flower.getKey()), flower.getValue());
        }
    }

    public HashMap<List<Integer>, Spot> getSpotsMap() {
        return AllSpots.spotsMap;
    }

    public static Spot getSpotObject(Integer x, Integer y) {
        return spotsMap.get(Stream.of(x, y).collect(Collectors.toList()));
    }

    public static Map<String, Integer> getFlowersStatistics(int deadPlants) {
        Map<String, Integer> flowersStatMap = new HashMap<>();

        flowersStatMap.put("Total flowers: ", (int) spotsMap.values().stream().filter(spot -> spot.getSpotObject().equals("Flower")).count());

        int seedNumber = 0;
        for (Map.Entry<List<Integer>, Spot> spot : spotsMap.entrySet()) {
            seedNumber += spot.getValue().getSpotSeedsOn().size();
        }
        flowersStatMap.put("Total SEED: ", seedNumber);

        flowersStatMap.put("Total SEEDLING: ", (int) spotsMap.values().stream().filter(spot -> spot.getSpotOccupancy() instanceof Flower && ((Flower) spot.getSpotOccupancy()).getPlantState().toString().equals("SEEDLING")).count());
        flowersStatMap.put("Total ADULT: ", (int) spotsMap.values().stream().filter(spot -> spot.getSpotOccupancy() instanceof Flower && ((Flower) spot.getSpotOccupancy()).getPlantState().toString().equals("ADULT")).count());
        flowersStatMap.put("Total BLOOMING: ", (int) spotsMap.values().stream().filter(spot -> spot.getSpotOccupancy() instanceof Flower && ((Flower) spot.getSpotOccupancy()).getPlantState().toString().equals("BLOOMING")).count());
        flowersStatMap.put("Total FRUITION: ", (int) spotsMap.values().stream().filter(spot -> spot.getSpotOccupancy() instanceof Flower && ((Flower) spot.getSpotOccupancy()).getPlantState().toString().equals("FRUITION")).count());
        flowersStatMap.put("Total DEAD: ", deadPlants);

        flowersStatMap.put("Total Dandelions: ", (int) spotsMap.values().stream().filter(spot -> spot.getSpotOccupancy() instanceof Dandelion).count());
        flowersStatMap.put("Total Chamomile: ", (int) spotsMap.values().stream().filter(spot -> spot.getSpotOccupancy() instanceof Chamomile).count());
        return flowersStatMap;
    }
}
