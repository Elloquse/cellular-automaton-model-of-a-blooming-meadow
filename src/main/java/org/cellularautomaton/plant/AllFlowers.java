package org.cellularautomaton.plant;

import org.cellularautomaton.meadow.AllSpots;
import org.cellularautomaton.meadow.Spot;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AllFlowers {

    static ArrayList<Flower> flowerList = new ArrayList<>();

    public static ArrayList<Flower> getFlowerList() {
        return flowerList;
    }

    // Размещение цветка определенного вида на клетке
    public static void placeFlower(Spot spot, String flower) {
        Flower newFlower = null;
        if (flower.equals("Chamomile")) {
            newFlower = new Chamomile();
        } else if (flower.equals("Dandelion")) {
            newFlower = new Dandelion();
        }
        assert newFlower != null;
        newFlower.setPlantSpot(spot);
        newFlower.getPlantSpot().getSpotSeedsOn().add(newFlower);

        if (!AllFlowers.getFlowerList().contains(newFlower)) {
            AllFlowers.getFlowerList().add(newFlower);
        }
    }

    public static void tick() {
        List<Flower> flowerListToIterate = new ArrayList<>(AllFlowers.getFlowerList());

        for (Flower flower : flowerListToIterate) {
            flower.tick();
        }
    }

    public static void removeSeeds(List<Flower> listOfSeeds) {
        AllFlowers.getFlowerList().removeAll(listOfSeeds);
    }

    public static void addPlant(Flower flower) {
        AllFlowers.getFlowerList().add(flower);
    }

    public static ArrayList<String> getStatistics() {
        Map<String, Integer> plantMapStats = AllSpots.getFlowersStatistics((int) flowerList.stream().filter(plant -> plant.getPlantState().toString().equals("DEAD")).count());
        ArrayList<String> flowerStats = new ArrayList<>();
        flowerStats.add("-----Plants statistics-----");
        flowerStats.add("Total flowers: " + plantMapStats.get("Total flowers: "));
        flowerStats.add("    Total SEED: " + plantMapStats.get("Total SEED: "));
        flowerStats.add("    Total SEEDLING: " + plantMapStats.get("Total SEEDLING: "));
        flowerStats.add("    Total ADULT: " + plantMapStats.get("Total ADULT: "));
        flowerStats.add("    Total BLOOMING: " + plantMapStats.get("Total BLOOMING: "));
        flowerStats.add("    Total FRUITION: " + plantMapStats.get("Total FRUITION: "));
        flowerStats.add("    Total DEAD: " + plantMapStats.get("Total DEAD: "));
        flowerStats.add("");
        flowerStats.add("    Total Dandelions: " + plantMapStats.get("Total Dandelions: "));
        flowerStats.add("    Total Chamomile: " + plantMapStats.get("Total Chamomile: "));
        flowerStats.add("");
        return flowerStats;
    }
}
