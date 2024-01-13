package org.cellularautomaton.plant;

public final class Chamomile extends Flower {

    private Enum<PlantState.ChamomileState> plantState;

    public Chamomile () {
        plantState = PlantState.ChamomileState.SEED;
    }
    public Enum<PlantState.ChamomileState> getPlantState() {
        return this.plantState;
    }

    public void setPlantState(Enum newPlantState) {
        this.plantState = newPlantState;
    }
}
