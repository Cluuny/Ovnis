package co.edu.uptc.presenter;

import co.edu.uptc.model.OVNI;
import co.edu.uptc.model.SimulationParameters;
import co.edu.uptc.model.SimulationEngine;
import co.edu.uptc.view.View;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Main {
    private View view;
    private SimulationEngine simulationEngine;
    private BufferedImage ovniImage;

    public static void main(String[] args) {
        View view = new View();
        view.setVisible(true);
        Main mainPresenter = new Main(view);

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

        view.getControlPanel().getSelectImageButton().addActionListener(e -> mainPresenter.selectImage());
    }

    public Main(View view) {
        this.view = view;
    }

    public void selectImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(view);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                ovniImage = ImageIO.read(selectedFile);
                view.getDisplayPanel().setOvniImage(ovniImage);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(view, "No se pudo cargar la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void startSimulation(int numOvnis, int speed, int appearanceInterval) {
        if (simulationEngine != null) {
            simulationEngine.stopSimulation();
        }

        SimulationParameters parameters = new SimulationParameters(numOvnis, appearanceInterval, speed);
        
        simulationEngine = new SimulationEngine(
                parameters,
                view.getDisplayPanel().getWidth(),
                view.getDisplayPanel().getHeight(),
                view.getDisplayPanel().getDestinationX(),
                view.getDisplayPanel().getDestinationY(),
                view.getDisplayPanel().getDestinationRadius()
        );

        simulationEngine.startWithInterval(view.getDisplayPanel(), view.getInfoPanel());
    }
}
