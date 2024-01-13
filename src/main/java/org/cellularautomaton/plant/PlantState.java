package org.cellularautomaton.plant;


public final class PlantState {

    public static Enum changeState(Flower flower, String toWhichState) {
        if (flower.getClass().getSimpleName().equals("Chamomile")) {
            return PlantState.ChamomileState.valueOf(toWhichState.toUpperCase());
        } else if (flower.getClass().getSimpleName().equals("Dandelion")) {
            return PlantState.DandelionState.valueOf(toWhichState.toUpperCase());
        } else {
            return null;
        }
    }

    public static int getLifeTime(Flower flower) {
        if (flower.getClass().getSimpleName().equals("Chamomile")) {
            return ChamomileState.valueOf(flower.getPlantState().toString()).lifetime;
        } else if (flower.getClass().getSimpleName().equals("Dandelion")) {
            return DandelionState.valueOf(flower.getPlantState().toString()).lifetime;
        } else {
            return 0;
        }
    }

    enum ChamomileState {
        SEED (1),
        SEEDLING (1),
        ADULT (1),
        BLOOMING (10),
        FRUITION (10),
        DEAD (0);

        private final int lifetime;

        ChamomileState(int lifetime) {
            this.lifetime = lifetime;
        }
    }

    enum DandelionState {
        SEED (1),
        SEEDLING (1),
        ADULT (1),
        BLOOMING (10),
        FRUITION (10),
        DEAD (0);

        private final int lifetime;

        DandelionState(int lifetime) {
            this.lifetime = lifetime;
        }
    }

}
