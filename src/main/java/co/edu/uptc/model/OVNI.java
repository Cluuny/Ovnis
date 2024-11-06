package co.edu.uptc.model;

import java.awt.Image;

public class OVNI {
    private int x;
    private int y;
    private int speed;
    private boolean crashed;
    private Image image;
    private int angle;
    private boolean hasDestination = false;
    private int destinationX;
    private int destinationY;

    public OVNI(int x, int y, int speed, Image image) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.crashed = false;
        this.image = image;
        this.angle = (int) (Math.random() * 360);
    }

    public void setDestination(int destinationX, int destinationY) {
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.hasDestination = true;
    }

    public boolean hasDestination() {
        return hasDestination;
    }

    public int getDestinationX() {
        return destinationX;
    }

    public int getDestinationY() {
        return destinationY;
    }

    public void clearDestination() {
        this.hasDestination = false;
    }

    // Getters y setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isCrashed() {
        return crashed;
    }

    public void setCrashed(boolean crashed) {
        this.crashed = crashed;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }
}
