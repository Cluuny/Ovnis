package co.edu.uptc.view;

import javax.swing.*;

import lombok.Getter;

import java.awt.*;

@Getter
public class ControlPanelView extends JPanel {
    private JTextField numOvnisField;
    private JTextField speedField;
    private JTextField individualSpeedField;
    private JTextField appearanceIntervalField;
    private JButton startButton;
    private JButton setSpeedButton;
    private JButton selectImageButton;

    public ControlPanelView() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        JLabel appearanceIntervalLabel = new JLabel("Intervalo de aparición (ms):");
        appearanceIntervalField = new JTextField(5);
        JLabel numOvnisLabel = new JLabel("Número de OVNIS:");
        numOvnisField = new JTextField(5);
        JLabel speedLabel = new JLabel("Velocidad:");
        speedField = new JTextField(5);
        JLabel individualSpeedLabel = new JLabel("Velocidad individual:");
        individualSpeedField = new JTextField(5);

        inputPanel.add(appearanceIntervalLabel);
        inputPanel.add(numOvnisLabel);
        inputPanel.add(speedLabel);
        inputPanel.add(individualSpeedLabel);
        inputPanel.add(appearanceIntervalField);
        inputPanel.add(numOvnisField);
        inputPanel.add(speedField);
        inputPanel.add(individualSpeedField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        startButton = new JButton("Iniciar Simulación");
        setSpeedButton = new JButton("Asignar Velocidad");
        selectImageButton = new JButton("Seleccionar Imagen");

        buttonPanel.add(startButton);
        buttonPanel.add(setSpeedButton);
        buttonPanel.add(selectImageButton);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }
}
