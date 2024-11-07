package co.edu.uptc.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

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
    private List<Point> customPath = new ArrayList<>();

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
        customPath.clear();
    }

    public void addPointToPath(Point point) {
        customPath.add(point);
    }

    public boolean hasCustomPath() {
        return !customPath.isEmpty();
    }

    public Point getNextPathPoint() {
        return customPath.isEmpty() ? null : customPath.remove(0);
    }

    public void clearCustomPath() {
        customPath.clear();
    }
}
