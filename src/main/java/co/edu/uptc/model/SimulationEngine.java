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
        // Detener cualquier hilo previo antes de iniciar una nueva simulación
        stopSimulation();

        // Hilo para crear OVNIS en intervalos
        creationThread = new Thread(() -> {
            for (int i = 0; i < parameters.getNumberOfOvnis(); i++) {
                int x = (int) (Math.random() * areaWidth);
                int y = (int) (Math.random() * areaHeight);
                int speed = parameters.getDefaultSpeed();

                OVNI ovni = new OVNI(x, y, speed, null); // null como placeholder de la imagen
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

        // Hilo continuo para mover todos los OVNIS y actualizar contadores
        movementThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                ovniManager.updatePositions(areaWidth, areaHeight);
                displayPanel.repaint();

                // Actualizar contadores en el panel de información
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

    // Método para detener los hilos de creación y movimiento
    public void stopSimulation() {
        if (creationThread != null && creationThread.isAlive()) {
            creationThread.interrupt();
        }
        if (movementThread != null && movementThread.isAlive()) {
            movementThread.interrupt();
        }

        // Reiniciar el estado de la simulación
        ovnis.clear();
    }

    private void updateInfoPanel(InfoPanelView infoPanel) {
        int movingCount = ovniManager.getMovingCount();
        int crashedCount = ovniManager.getCrashedCount();
        infoPanel.updateMovingCount(movingCount);
        infoPanel.updateCrashedCount(crashedCount);
    }
}
