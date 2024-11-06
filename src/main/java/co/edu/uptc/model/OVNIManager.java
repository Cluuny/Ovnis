package co.edu.uptc.model;

import java.util.List;

public class OVNIManager {
    private List<OVNI> ovnis;
    private int destinationX;
    private int destinationY;
    private int destinationRadius;
    private boolean hasDestination;

    public OVNIManager(List<OVNI> ovnis, int destinationX, int destinationY, int destinationRadius) {
        this.ovnis = ovnis;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.destinationRadius = destinationRadius;
        this.hasDestination = true;
    }

    public void updatePositions(int areaWidth, int areaHeight) {
        for (OVNI ovni : ovnis) {
            if (!ovni.isCrashed()) {
                // Si el OVNI tiene un destino específico, mueve el OVNI hacia ese punto
                if (ovni.hasDestination()) {
                    int deltaX = ovni.getDestinationX() - ovni.getX();
                    int deltaY = ovni.getDestinationY() - ovni.getY();
                    double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                    // Verificar si el OVNI ha llegado al destino
                    if (distance <= ovni.getSpeed()) {
                        ovni.setX(ovni.getDestinationX());
                        ovni.setY(ovni.getDestinationY());
                        ovni.clearDestination(); // Limpiar el destino cuando se llega
                    } else {
                        // Calcular el movimiento hacia el destino
                        int moveX = (int) (ovni.getSpeed() * deltaX / distance);
                        int moveY = (int) (ovni.getSpeed() * deltaY / distance);
                        ovni.setX(ovni.getX() + moveX);
                        ovni.setY(ovni.getY() + moveY);
                    }
                } else {
                    // Mover el OVNI en la dirección original si no tiene destino
                    int newX = ovni.getX() + (int) (ovni.getSpeed() * Math.cos(Math.toRadians(ovni.getAngle())));
                    int newY = ovni.getY() + (int) (ovni.getSpeed() * Math.sin(Math.toRadians(ovni.getAngle())));

                    // Verificar si el OVNI está fuera de los límites del área
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

    private void checkCollisionWithOtherOvnis(OVNI ovni) {
        for (OVNI otherOvni : ovnis) {
            // Evitar la auto-colisión y solo verificar si ambos OVNIS no están
            // "estrellados"
            if (ovni != otherOvni && !otherOvni.isCrashed() && !ovni.isCrashed()) {
                int deltaX = ovni.getX() - otherOvni.getX();
                int deltaY = ovni.getY() - otherOvni.getY();
                int distanceSquared = deltaX * deltaX + deltaY * deltaY;

                // Comprobar si la distancia es menor o igual al diámetro de colisión (20
                // unidades)
                if (distanceSquared <= 400) { // 20*20 (radio de colisión al cuadrado)
                    ovni.setCrashed(true);
                    otherOvni.setCrashed(true);
                }
            }
        }
    }

    private boolean isInDestination(OVNI ovni) {
        // Calcula la distancia al cuadrado entre el OVNI y el centro del área de
        // destino
        int deltaX = ovni.getX() - destinationX;
        int deltaY = ovni.getY() - destinationY;
        int distanceSquared = deltaX * deltaX + deltaY * deltaY;

        // Compara la distancia al cuadrado con el radio al cuadrado del área de destino
        return distanceSquared <= (destinationRadius * destinationRadius);
    }

    public int getMovingCount() {
        return (int) ovnis.stream().filter(ovni -> !ovni.isCrashed()).count();
    }

    public int getCrashedCount() {
        return (int) ovnis.stream().filter(OVNI::isCrashed).count();
    }

}
