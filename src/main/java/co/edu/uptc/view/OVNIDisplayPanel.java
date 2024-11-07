package co.edu.uptc.view;

import co.edu.uptc.model.OVNI;
import lombok.Getter;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

@Getter
public class OVNIDisplayPanel extends JPanel {
    private List<OVNI> ovnis;
    private OVNI selectedOvni;
    private int destinationX = 600;
    private int destinationY = 300;
    private int destinationRadius = 50;
    private Image ovniImage;

    private static final int MIN_DISTANCE_BETWEEN_POINTS = 5;

    public OVNIDisplayPanel() {
        setBackground(Color.LIGHT_GRAY);

        MouseAdapter mouseAdapter = new MouseAdapter() {
            private Point lastPoint;

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (selectedOvni != null) {
                        selectedOvni.clearCustomPath();
                        lastPoint = new Point(e.getX(), e.getY());
                        selectedOvni.addPointToPath(lastPoint);
                    }
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    selectedOvni = null;
                    for (OVNI ovni : ovnis) {
                        if (!ovni.isCrashed() && isMouseOverOvni(e.getX(), e.getY(), ovni)) {
                            selectedOvni = ovni;
                            repaint();
                            break;
                        }
                    }
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedOvni != null && (e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
                    Point currentPoint = new Point(e.getX(), e.getY());
                    if (lastPoint.distance(currentPoint) >= MIN_DISTANCE_BETWEEN_POINTS) {
                        selectedOvni.addPointToPath(currentPoint);
                        lastPoint = currentPoint;
                        repaint();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && selectedOvni != null) {
                    selectedOvni.setDestination(destinationX, destinationY);
                    repaint();
                }
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    private boolean isMouseOverOvni(int mouseX, int mouseY, OVNI ovni) {
        int radius = 20;
        return Math.abs(mouseX - ovni.getX()) < radius && Math.abs(mouseY - ovni.getY()) < radius;
    }

    public void setOvnis(List<OVNI> ovnis) {
        this.ovnis = ovnis;
        repaint();
    }

    public void setOvniImage(Image image) {
        this.ovniImage = image;
        repaint();
    }

    public void setSelectedOvniSpeed(int speed) {
        if (selectedOvni != null) {
            selectedOvni.setSpeed(speed);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawOval(destinationX - destinationRadius, destinationY - destinationRadius, destinationRadius * 2, destinationRadius * 2);

        if (ovnis != null) {
            for (OVNI ovni : ovnis) {
                if (!ovni.isCrashed()) {
                    if (ovni == selectedOvni) {
                        g.setColor(Color.BLACK);
                        g.drawOval(ovni.getX() - 2, ovni.getY() - 2, 24, 24);
                    }

                    if (ovniImage != null) {
                        g.drawImage(ovniImage, ovni.getX(), ovni.getY(), 20, 20, this);
                    } else {
                        g.setColor(Color.RED);
                        g.fillOval(ovni.getX(), ovni.getY(), 20, 20);
                    }

                    if (!ovni.getCustomPath().isEmpty()) {
                        g.setColor(Color.BLUE);
                        Point lastPoint = new Point(ovni.getX(), ovni.getY());
                        for (Point point : ovni.getCustomPath()) {
                            g.drawLine(lastPoint.x, lastPoint.y, point.x, point.y);
                            lastPoint = point;
                        }
                    }

                    if (ovni.hasDestination()) {
                        g.setColor(Color.BLACK);
                        g.drawLine(ovni.getX() + 10, ovni.getY() + 10, ovni.getDestinationX(), ovni.getDestinationY());
                    }
                }
            }
        }
    }
}
