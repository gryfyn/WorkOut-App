package com.example.keepfit;

public class ExerciseData {
    private int sets;
    private int reps;
    private double weights;

    public ExerciseData(int sets, int reps, double weights) {
        this.sets = sets;
        this.reps = reps;
        this.weights = weights;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public double getWeights() {
        return weights;
    }
}
