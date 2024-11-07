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
import java.util.ArrayList;
import java.util.List;

public class Main {
    private View view;
    private List<OVNI> ovnis;
    private SimulationEngine simulationEngine;
    private BufferedImage ovniImage; // Imagen del OVNI

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

        // Configurar acción del botón de selección de imagen
        view.getControlPanel().getSelectImageButton().addActionListener(e -> mainPresenter.selectImage());
    }

    public Main(View view) {
        this.view = view;
        this.ovnis = new ArrayList<>();
    }

    // Método para seleccionar la imagen
    public void selectImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(view);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                ovniImage = ImageIO.read(selectedFile); // Cargar la imagen seleccionada
                view.getDisplayPanel().setOvniImage(ovniImage); // Pasar la imagen al panel de visualización
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
