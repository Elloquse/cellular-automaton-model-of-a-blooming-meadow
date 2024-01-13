package org.cellularautomaton.plant;

public final class Dandelion extends Flower {

    private Enum plantState;

    public Dandelion () {
        plantState = PlantState.DandelionState.SEED;
    }

    @Override
    public Enum getPlantState() {
        return this.plantState;
    }

    @Override
    public void setPlantState(Enum newPlantState) {
        this.plantState = newPlantState;
    }
}
