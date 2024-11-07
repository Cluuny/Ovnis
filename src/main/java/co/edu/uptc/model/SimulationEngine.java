package co.edu.uptc.model;

import java.util.concurrent.CopyOnWriteArrayList;
import co.edu.uptc.view.OVNIDisplayPanel;
import co.edu.uptc.view.InfoPanelView;

public class SimulationEngine {
    private CopyOnWriteArrayList<OVNI> ovnis;
    private SimulationParameters parameters;
    private int areaWidth;
    private int areaHeight;
    private OVNIManager ovniManager;
    private Thread creationThread;
    private Thread movementThread;

    public SimulationEngine(SimulationParameters parameters, int areaWidth, int areaHeight, int destinationX, int destinationY, int destinationRadius) {
        this.parameters = parameters;
        this.areaWidth = areaWidth;
        this.areaHeight = areaHeight;
        this.ovnis = new CopyOnWriteArrayList<>();
        this.ovniManager = new OVNIManager(ovnis, destinationX, destinationY, destinationRadius);
    }

    public void startWithInterval(OVNIDisplayPanel displayPanel, InfoPanelView infoPanel) {
        stopSimulation();

        // Hilo para crear los OVNIS
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

        // Hilo para actualizar posiciones
        movementThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                ovniManager.updatePositions(areaWidth, areaHeight);
                displayPanel.repaint();
                updateInfoPanel(infoPanel);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        movementThread.start();
    }

    public void stopSimulation() {
        if (creationThread != null && creationThread.isAlive()) {
            creationThread.interrupt();
        }
        if (movementThread != null && movementThread.isAlive()) {
            movementThread.interrupt();
        }
        ovnis.clear();
    }

    private void updateInfoPanel(InfoPanelView infoPanel) {
        int movingCount = ovniManager.getMovingCount();
        int crashedCount = ovniManager.getCrashedCount();
        infoPanel.updateMovingCount(movingCount);
        infoPanel.updateCrashedCount(crashedCount);
    }
}
