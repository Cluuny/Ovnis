package co.edu.uptc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SimulationParameters {
    private int numberOfOvnis;
    private int appearanceInterval;
    private int defaultSpeed;
}
