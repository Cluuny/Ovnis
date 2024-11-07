package co.edu.uptc.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OVNI {
    private int x;
    private int y;
    private int speed;
    private boolean crashed;
    private int angle;
    private boolean hasDestination = false;
    private int destinationX;
    private int destinationY;

    public OVNI(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.crashed = false;
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

    public void clearDestination() {
        this.hasDestination = false;
    }

    public boolean isCrashed() {
        return crashed;
    }

    public void setCrashed(boolean crashed) {
        this.crashed = crashed;
        clearDestination();
    }

    public int getAngle() {
        return angle;
    }
}
