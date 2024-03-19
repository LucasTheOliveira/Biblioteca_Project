package Components.customTitle;

import javax.swing.*;
import java.awt.*;

// CLASSE DO TITULO BIBLIOTECA UNAERP
public class TitlePanel extends JPanel {
    public TitlePanel() {
        setLayout(new BorderLayout());

// DEFINE AS PROPRIEDADES DO TITULO
        JLabel titleLabel = new JLabel("Biblioteca UNAERP");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        setBackground(new Color(0, 120, 215));
        setPreferredSize(new Dimension(getPreferredSize().width, 40));

        add(titleLabel, BorderLayout.CENTER);
    }

// DEFINE A LOCALIZAÇÃO DO TITULO
    public GridBagConstraints getConstraints() {
        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 0;
        titleConstraints.gridwidth = 2;
        titleConstraints.fill = GridBagConstraints.HORIZONTAL;
        return titleConstraints;
    }
}