package co.edu.uptc.presenter;

import co.edu.uptc.model.OVNI;
import co.edu.uptc.model.SimulationParameters;
import co.edu.uptc.model.SimulationEngine;
import co.edu.uptc.view.View;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private View view;
    private List<OVNI> ovnis;
    private SimulationEngine simulationEngine;

    public static void main(String[] args) {
        View view = new View();
        view.setVisible(true);
        Main mainPresenter = new Main(view);

        // Configurar acción del botón de inicio
        view.getControlPanel().getStartButton().addActionListener(e -> {
            try {
                int numOvnis = Integer.parseInt(view.getControlPanel().getNumOvnisField().getText());
                int speed = Integer.parseInt(view.getControlPanel().getSpeedField().getText());
                int appearanceInterval = Integer.parseInt(view.getControlPanel().getAppearanceIntervalField().getText());

                mainPresenter.startSimulation(numOvnis, speed, appearanceInterval);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Por favor, ingrese valores numéricos válidos.", "Error de entrada",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Configurar acción para cambiar la velocidad del OVNI seleccionado
        view.getControlPanel().getSetSpeedButton().addActionListener(e -> {
            OVNI selectedOvni = view.getDisplayPanel().getSelectedOvni();
            if (selectedOvni != null) {
                try {
                    int newSpeed = Integer.parseInt(view.getControlPanel().getIndividualSpeedField().getText());
                    selectedOvni.setSpeed(newSpeed);
                    JOptionPane.showMessageDialog(view, "Velocidad asignada al OVNI seleccionado.", "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Por favor, ingrese un valor numérico válido para la velocidad.",
                            "Error de entrada", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(view, "Seleccione un OVNI antes de asignar la velocidad.", "Advertencia",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    public Main(View view) {
        this.view = view;
        this.ovnis = new ArrayList<>();
    }

    public void startSimulation(int numOvnis, int speed, int appearanceInterval) {
        // Detener la simulación si ya está en ejecución
        if (simulationEngine != null) {
            simulationEngine.stopSimulation();
        }

        // Configurar nuevos parámetros para la simulación
        SimulationParameters parameters = new SimulationParameters(numOvnis, appearanceInterval, speed);
        
        // Inicializar SimulationEngine con los nuevos parámetros y dimensiones de la vista
        simulationEngine = new SimulationEngine(
                parameters,
                view.getDisplayPanel().getWidth(),
                view.getDisplayPanel().getHeight(),
                view.getDisplayPanel().getDestinationX(),
                view.getDisplayPanel().getDestinationY(),
                view.getDisplayPanel().getDestinationRadius()
        );

        // Iniciar la simulación con el nuevo estado
        simulationEngine.startWithInterval(view.getDisplayPanel(), view.getInfoPanel());
    }
}
