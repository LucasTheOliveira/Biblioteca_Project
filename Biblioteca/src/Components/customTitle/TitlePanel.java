package Components.customTitle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class TitlePanel extends JPanel {

    public TitlePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(0, 120, 215));
        setPreferredSize(new Dimension(getPreferredSize().width, 40));

        JLabel titleLabel = new JLabel("Biblioteca UNAERP");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(titleLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        JButton logoutButton = new JButton(resizeIcon(new ImageIcon(getClass().getResource("/icons/logout.png"))));
        JButton userListButton = new JButton(resizeIcon(new ImageIcon(getClass().getResource("/icons/user.png"))));

        logoutButton.setBackground(Color.WHITE);
        logoutButton.setFocusable(false);
        logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutButton.setToolTipText("Logout");

        userListButton.setBackground(Color.WHITE);
        userListButton.setFocusable(false);
        userListButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        userListButton.setToolTipText("Lista de Usuários");

        buttonPanel.add(logoutButton);
        buttonPanel.add(userListButton);
        buttonPanel.setBorder(new EmptyBorder(0, 0, 0, 80));

        add(buttonPanel, BorderLayout.EAST);
    }

    public GridBagConstraints getConstraints() {
        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 0;
        titleConstraints.gridwidth = 2;
        titleConstraints.fill = GridBagConstraints.HORIZONTAL;
        return titleConstraints;
    }

    private ImageIcon resizeIcon(ImageIcon icon) {
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(22, 22, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }
}