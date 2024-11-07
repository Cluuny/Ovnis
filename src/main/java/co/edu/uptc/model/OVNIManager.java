package co.edu.uptc.model;

import java.util.concurrent.CopyOnWriteArrayList;
import java.awt.Point;

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
                if (isInDestinationArea(ovni)) {
                    ovni.setCrashed(true);
                    continue;
                }

                if (ovni.hasCustomPath()) {
                    Point nextPoint = ovni.getCustomPath().get(0);
                    moveOvniTowards(ovni, nextPoint.x, nextPoint.y);

                    if (ovni.getX() == nextPoint.x && ovni.getY() == nextPoint.y) {
                        ovni.getCustomPath().remove(0);
                    }
                } 
                else if (ovni.hasDestination()) {
                    moveOvniTowards(ovni, ovni.getDestinationX(), ovni.getDestinationY());
                } 
                else {
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

        checkCollisions();
    }

    private void moveOvniTowards(OVNI ovni, int targetX, int targetY) {
        int deltaX = targetX - ovni.getX();
        int deltaY = targetY - ovni.getY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance <= ovni.getSpeed()) {
            ovni.setX(targetX);
            ovni.setY(targetY);
        } else {
            int moveX = (int) (ovni.getSpeed() * deltaX / distance);
            int moveY = (int) (ovni.getSpeed() * deltaY / distance);
            ovni.setX(ovni.getX() + moveX);
            ovni.setY(ovni.getY() + moveY);
        }
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

                    if (distance < 20) {
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
