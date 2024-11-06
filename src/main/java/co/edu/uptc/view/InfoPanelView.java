package co.edu.uptc.view;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanelView extends JPanel {
    private JLabel movingLabel;
    private JLabel crashedLabel;

    public InfoPanelView() {
        movingLabel = new JLabel("OVNIS en movimiento: 0");
        crashedLabel = new JLabel("OVNIS estrellados: 0");

        add(movingLabel);
        add(crashedLabel);
    }

    public void updateMovingCount(int count) {
        movingLabel.setText("OVNIS en movimiento: " + count);
    }

    public void updateCrashedCount(int count) {
        crashedLabel.setText("OVNIS estrellados: " + count);
    }
}
