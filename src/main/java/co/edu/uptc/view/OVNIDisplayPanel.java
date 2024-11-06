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

        // Seleccionar OVNI con un clic y definir trayectoria con otro clic
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedOvni != null) {
                    // Si ya hay un OVNI seleccionado, establece el destino
                    selectedOvni.setDestination(e.getX(), e.getY());
                } else {
                    // Selecciona el OVNI en la posición del clic si aún no hay ninguno seleccionado
                    for (OVNI ovni : ovnis) {
                        if (!ovni.isCrashed() && isMouseOverOvni(e.getX(), e.getY(), ovni)) {
                            selectedOvni = ovni;
                            break;
                        }
                    }
                }
                repaint();
            }
        });
    }

    private boolean isMouseOverOvni(int mouseX, int mouseY, OVNI ovni) {
        int radius = 20; // Radio del óvalo que representa al OVNI
        return Math.abs(mouseX - ovni.getX()) < radius && Math.abs(mouseY - ovni.getY()) < radius;
    }

    public void setOvnis(List<OVNI> ovnis) {
        this.ovnis = ovnis;
        repaint();
    }

    public OVNI getSelectedOvni() {
        return selectedOvni;
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
                        g.setColor(Color.BLUE); // Color especial para el OVNI seleccionado
                    } else {
                        g.setColor(Color.RED);
                    }
                    g.fillOval(ovni.getX(), ovni.getY(), 20, 20);

                    // Dibujar la línea de trayectoria si el OVNI tiene un destino
                    if (ovni.hasDestination()) {
                        g.setColor(Color.YELLOW);
                        g.drawLine(ovni.getX() + 10, ovni.getY() + 10, ovni.getDestinationX(), ovni.getDestinationY());
                    }
                }
            }
        }
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
