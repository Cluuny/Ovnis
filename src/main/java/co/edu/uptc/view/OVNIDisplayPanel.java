package co.edu.uptc.view;

import co.edu.uptc.model.OVNI;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class OVNIDisplayPanel extends JPanel {
    private List<OVNI> ovnis;
    private OVNI selectedOvni;
    private int destinationX = 600; // Coordenadas fijas para el punto de destino inicial
    private int destinationY = 300;
    private int destinationRadius = 50; // Tamaño del área de destino

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

                if (!ovniClicked && selectedOvni != null) {
                    selectedOvni.setDestination(e.getX(), e.getY());
                    selectedOvni = null; // Deseleccionar después de establecer el destino
                }
                repaint();
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
                    g.setColor(ovni == selectedOvni ? Color.BLUE : Color.RED);
                    g.fillOval(ovni.getX(), ovni.getY(), 20, 20);

                    if (ovni.hasDestination()) {
                        g.setColor(Color.YELLOW);
                        g.drawLine(ovni.getX() + 10, ovni.getY() + 10, ovni.getDestinationX(), ovni.getDestinationY());
                    }
                }
            }
        }
    }

    // Método para obtener el OVNI seleccionado
    public OVNI getSelectedOvni() {
        return selectedOvni;
    }

    // Métodos para obtener las coordenadas y el radio del área de destino
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
