package co.edu.uptc.view;

import co.edu.uptc.model.OVNI;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class OVNIDisplayPanel extends JPanel {
    private List<OVNI> ovnis;
    private OVNI selectedOvni;
    private int destinationX = 600;
    private int destinationY = 300;
    private int destinationRadius = 50;
    private Image ovniImage; // Imagen de OVNI seleccionada por el usuario

    public OVNIDisplayPanel() {
        setBackground(Color.LIGHT_GRAY);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean ovniClicked = false;

                // Intentar seleccionar un OVNI al hacer clic
                for (OVNI ovni : ovnis) {
                    if (!ovni.isCrashed() && isMouseOverOvni(e.getX(), e.getY(), ovni)) {
                        selectedOvni = ovni;
                        ovniClicked = true;
                        break;
                    }
                }

                if (ovniClicked) {
                    // Si se hizo clic en un OVNI, seleccionarlo y no asignar destino
                    repaint();
                } else if (selectedOvni != null) {
                    // Si se hizo clic fuera de un OVNI y hay uno seleccionado, asignar el destino
                    selectedOvni.setDestination(e.getX(), e.getY());
                    selectedOvni = null; // Deseleccionar después de asignar el destino
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

        // Dibujar el área de destino
        g.setColor(Color.GREEN);
        g.drawOval(destinationX - destinationRadius, destinationY - destinationRadius, destinationRadius * 2, destinationRadius * 2);

        // Dibujar los OVNIS y sus trayectorias
        if (ovnis != null) {
            for (OVNI ovni : ovnis) {
                if (!ovni.isCrashed()) {
                    if (ovni == selectedOvni) {
                        // Dibujar un contorno alrededor del OVNI seleccionado
                        g.setColor(Color.YELLOW); // Color del contorno para el OVNI seleccionado
                        g.drawOval(ovni.getX() - 2, ovni.getY() - 2, 24, 24); // Contorno más grande que el OVNI
                    }

                    // Dibujar la imagen del OVNI
                    if (ovniImage != null) {
                        g.drawImage(ovniImage, ovni.getX(), ovni.getY(), 20, 20, this);
                    } else {
                        g.setColor(Color.RED);
                        g.fillOval(ovni.getX(), ovni.getY(), 20, 20);
                    }

                    // Dibujar la línea de trayectoria
                    if (ovni.hasDestination()) {
                        g.setColor(Color.YELLOW);
                        g.drawLine(ovni.getX() + 10, ovni.getY() + 10, ovni.getDestinationX(), ovni.getDestinationY());
                    }
                }
            }
        }
    }

    public OVNI getSelectedOvni() {
        return selectedOvni;
    }

    public int getDestinationX() {
        return destinationX;
    }

    public int getDestinationY() {
        return destinationY;
    }

    public int getDestinationRadius() {
        return destinationRadius;
    }
}
