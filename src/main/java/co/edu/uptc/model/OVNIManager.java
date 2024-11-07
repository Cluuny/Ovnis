package co.edu.uptc.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OVNIManager {
    private CopyOnWriteArrayList<OVNI> ovnis;
    private int destinationX;
    private int destinationY;
    private int destinationRadius;

    public OVNIManager(CopyOnWriteArrayList<OVNI> ovnis, int destinationX, int destinationY, int destinationRadius) {
        this.ovnis = ovnis;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.destinationRadius = destinationRadius;
    }

    public void updatePositions(int areaWidth, int areaHeight) {
        for (OVNI ovni : ovnis) {
            if (!ovni.isCrashed()) {
                // Verificar si el OVNI ha llegado al área de destino
                if (isInDestinationArea(ovni)) {
                    ovni.setCrashed(true);  // Marcar como "destruido" si llega al destino
                    continue;
                }

                // Movimiento hacia el destino si tiene uno
                if (ovni.hasDestination()) {
                    int deltaX = ovni.getDestinationX() - ovni.getX();
                    int deltaY = ovni.getDestinationY() - ovni.getY();
                    double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                    if (distance <= ovni.getSpeed()) {
                        ovni.setX(ovni.getDestinationX());
                        ovni.setY(ovni.getDestinationY());
                        ovni.clearDestination();
                    } else {
                        int moveX = (int) (ovni.getSpeed() * deltaX / distance);
                        int moveY = (int) (ovni.getSpeed() * deltaY / distance);
                        ovni.setX(ovni.getX() + moveX);
                        ovni.setY(ovni.getY() + moveY);
                    }
                } else {
                    // Movimiento normal
                    int newX = ovni.getX() + (int) (ovni.getSpeed() * Math.cos(Math.toRadians(ovni.getAngle())));
                    int newY = ovni.getY() + (int) (ovni.getSpeed() * Math.sin(Math.toRadians(ovni.getAngle())));

                    if (newX < 0 || newX >= areaWidth || newY < 0 || newY >= areaHeight) {
                        ovni.setCrashed(true);
                    } else {
                        ovni.setX(newX);
                        ovni.setY(newY);
                    }
                }
            }
        }

        // Verificar colisiones entre OVNIS después de moverlos
        checkCollisions();
    }

    private boolean isInDestinationArea(OVNI ovni) {
        int deltaX = ovni.getX() - destinationX;
        int deltaY = ovni.getY() - destinationY;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        return distance <= destinationRadius;
    }

    private void checkCollisions() {
        for (OVNI ovni1 : ovnis) {
            for (OVNI ovni2 : ovnis) {
                if (ovni1 != ovni2 && !ovni1.isCrashed() && !ovni2.isCrashed()) {
                    int deltaX = ovni1.getX() - ovni2.getX();
                    int deltaY = ovni1.getY() - ovni2.getY();
                    double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                    if (distance < 20) { // Suponiendo que el radio del OVNI es 10
                        ovni1.setCrashed(true);
                        ovni2.setCrashed(true);
                    }
                }
            }
        }
    }

    public int getMovingCount() {
        int count = 0;
        for (OVNI ovni : ovnis) {
            if (!ovni.isCrashed()) {
                count++;
            }
        }
        return count;
    }

    public int getCrashedCount() {
        int count = 0;
        for (OVNI ovni : ovnis) {
            if (ovni.isCrashed()) {
                count++;
            }
        }
        return count;
    }
}
