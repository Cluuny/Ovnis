package co.edu.uptc.view;

import javax.swing.JFrame;

import lombok.Getter;

import java.awt.BorderLayout;

@Getter
public class View extends JFrame {
    private OVNIDisplayPanel displayPanel;
    private ControlPanelView controlPanel;
    private InfoPanelView infoPanel;

    public View() {
        setTitle("Simulación de OVNIS");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        displayPanel = new OVNIDisplayPanel();
        controlPanel = new ControlPanelView();
        infoPanel = new InfoPanelView();

        add(displayPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        add(infoPanel, BorderLayout.NORTH);

        setLocationRelativeTo(null);
    }
}
