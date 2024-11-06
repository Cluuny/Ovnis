package co.edu.uptc.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OVNIManager {
    private CopyOnWriteArrayList<OVNI> ovnis;
    private int destinationX;
    private int destinationY;
    private int destinationRadius;

    // Constructor con los parámetros necesarios
    public OVNIManager(CopyOnWriteArrayList<OVNI> ovnis, int destinationX, int destinationY, int destinationRadius) {
        this.ovnis = ovnis;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.destinationRadius = destinationRadius;
    }

    // Actualizar posiciones de los OVNIS
    public void updatePositions(int areaWidth, int areaHeight) {
        for (OVNI ovni : ovnis) {
            if (!ovni.isCrashed()) {
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
    }

    // Método para contar los OVNIS en movimiento
    public int getMovingCount() {
        int count = 0;
        for (OVNI ovni : ovnis) {
            if (!ovni.isCrashed()) {
                count++;
            }
        }
        return count;
    }

    // Método para contar los OVNIS estrellados
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
