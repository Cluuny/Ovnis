package co.edu.uptc.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class ControlPanelView extends JPanel {
    private JTextField numOvnisField;
    private JTextField speedField;
    private JTextField individualSpeedField; // Nuevo campo de texto para velocidad individual
    private JButton startButton;
    private JButton setSpeedButton; // Nuevo botón para asignar la velocidad individual
    private JTextField appearanceIntervalField;

    public ControlPanelView() {
        setLayout(new GridLayout(2, 3));

        JLabel numOvnisLabel = new JLabel("Número de OVNIS:");
        numOvnisField = new JTextField(5);
        JLabel speedLabel = new JLabel("Velocidad:");
        speedField = new JTextField(5);
        JLabel individualSpeedLabel = new JLabel("Velocidad individual:");
        individualSpeedField = new JTextField(5);
        startButton = new JButton("Iniciar Simulación");
        setSpeedButton = new JButton("Asignar Velocidad");
        JLabel appearanceIntervalLabel = new JLabel("Intervalo de aparición (ms):");
        appearanceIntervalField = new JTextField(5);
        
        add(appearanceIntervalLabel);
        add(appearanceIntervalField);
        add(numOvnisLabel);
        add(numOvnisField);
        add(speedLabel);
        add(speedField);
        add(startButton);

        add(individualSpeedLabel);
        add(individualSpeedField);
        add(setSpeedButton);
    }

    public JTextField getNumOvnisField() {
        return numOvnisField;
    }

    public JTextField getSpeedField() {
        return speedField;
    }

    public JTextField getIndividualSpeedField() {
        return individualSpeedField;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getSetSpeedButton() {
        return setSpeedButton;
    }
    
    public JTextField getAppearanceIntervalField() {
        return appearanceIntervalField;
    }
}
