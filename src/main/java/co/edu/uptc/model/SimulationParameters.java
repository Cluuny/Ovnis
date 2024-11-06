package co.edu.uptc.model;

public class SimulationParameters {
    private int numberOfOvnis;
    private int appearanceInterval;
    private int defaultSpeed;

    public SimulationParameters(int numberOfOvnis, int appearanceInterval, int defaultSpeed) {
        this.numberOfOvnis = numberOfOvnis;
        this.appearanceInterval = appearanceInterval;
        this.defaultSpeed = defaultSpeed;
    }

    // Getters y setters
    public int getNumberOfOvnis() {
        return numberOfOvnis;
    }

    public void setNumberOfOvnis(int numberOfOvnis) {
        this.numberOfOvnis = numberOfOvnis;
    }

    public int getAppearanceInterval() {
        return appearanceInterval;
    }

    public void setAppearanceInterval(int appearanceInterval) {
        this.appearanceInterval = appearanceInterval;
    }

    public int getDefaultSpeed() {
        return defaultSpeed;
    }

    public void setDefaultSpeed(int defaultSpeed) {
        this.defaultSpeed = defaultSpeed;
    }
}
