package co.edu.uptc.model;

import java.util.concurrent.CopyOnWriteArrayList;
import co.edu.uptc.view.OVNIDisplayPanel;
import co.edu.uptc.view.InfoPanelView;

public class SimulationEngine {
    private CopyOnWriteArrayList<OVNI> ovnis;
    private SimulationParameters parameters;
    private int areaWidth;
    private int areaHeight;
    private int destinationX;
    private int destinationY;
    private int destinationRadius;
    private OVNIManager ovniManager;
    private Thread creationThread;
    private Thread movementThread;

    // Constructor con los parámetros necesarios
    public SimulationEngine(SimulationParameters parameters, int areaWidth, int areaHeight, int destinationX, int destinationY, int destinationRadius) {
        this.parameters = parameters;
        this.areaWidth = areaWidth;
        this.areaHeight = areaHeight;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.destinationRadius = destinationRadius;
        this.ovnis = new CopyOnWriteArrayList<>();
        this.ovniManager = new OVNIManager(ovnis, destinationX, destinationY, destinationRadius);
    }

    // Método para iniciar la simulación con el intervalo de aparición y actualización
    public void startWithInterval(OVNIDisplayPanel displayPanel, InfoPanelView infoPanel) {
        stopSimulation();

        // Hilo para crear los OVNIS a intervalos
        creationThread = new Thread(() -> {
            for (int i = 0; i < parameters.getNumberOfOvnis(); i++) {
                int x = (int) (Math.random() * areaWidth);
                int y = (int) (Math.random() * areaHeight);
                int speed = parameters.getDefaultSpeed();

                OVNI ovni = new OVNI(x, y, speed);
                ovnis.add(ovni);
                displayPanel.setOvnis(ovnis);
                displayPanel.repaint();

                try {
                    Thread.sleep(parameters.getAppearanceInterval());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        creationThread.start();

        // Hilo para actualizar las posiciones de los OVNIS
        movementThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                ovniManager.updatePositions(areaWidth, areaHeight);
                displayPanel.repaint();
                updateInfoPanel(infoPanel);

                try {
                    Thread.sleep(50); // Controla la velocidad de actualización
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        movementThread.start();
    }

    // Método para detener la simulación
    public void stopSimulation() {
        if (creationThread != null && creationThread.isAlive()) {
            creationThread.interrupt();
        }
        if (movementThread != null && movementThread.isAlive()) {
            movementThread.interrupt();
        }
        ovnis.clear();
    }

    // Método para actualizar la información en el panel de información
    private void updateInfoPanel(InfoPanelView infoPanel) {
        int movingCount = ovniManager.getMovingCount();
        int crashedCount = ovniManager.getCrashedCount();
        infoPanel.updateMovingCount(movingCount);
        infoPanel.updateCrashedCount(crashedCount);
    }
}
