package co.edu.uptc.view;

import co.edu.uptc.model.OVNI;
import lombok.Getter;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
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

    public OVNIDisplayPanel() {
        setBackground(Color.LIGHT_GRAY);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean ovniClicked = false;

                for (OVNI ovni : ovnis) {
                    if (!ovni.isCrashed() && isMouseOverOvni(e.getX(), e.getY(), ovni)) {
                        selectedOvni = ovni;
                        ovniClicked = true;
                        break;
                    }
                }

                if (ovniClicked) {
                    repaint();
                } else if (selectedOvni != null) {
                    selectedOvni.setDestination(e.getX(), e.getY());
                    selectedOvni = null;
                    repaint();
                }
            }
        });
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

                    if (ovni.hasDestination()) {
                        g.setColor(Color.BLACK);
                        g.drawLine(ovni.getX() + 10, ovni.getY() + 10, ovni.getDestinationX(), ovni.getDestinationY());
                    }
                }
            }
        }
    }
}
