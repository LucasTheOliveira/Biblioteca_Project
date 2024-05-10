package Components.CustomDialogs;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class CustomDeleteConfirmationDialog extends JDialog {
    private boolean confirmed = false;

    public CustomDeleteConfirmationDialog(JFrame parent, String message, ImageIcon icon) {
        super(parent, "", true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setBackground(Color.WHITE);

        JLabel iconLabel = new JLabel(resizeIcon(icon));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel label = new JLabel(message);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        iconPanel.setBackground(Color.WHITE);
        iconPanel.add(iconLabel);

        JButton confirmButton = new JButton("Confirmar");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 16));
        confirmButton.setBackground(new Color(0, 0, 139));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusable(false);
        confirmButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmed = true;
                dispose();
            }
        });

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setBackground(new Color(240, 240, 240));
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusable(false);
        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        contentPane.setBackground(Color.WHITE);
        contentPane.add(iconPanel, BorderLayout.NORTH);
        contentPane.add(label, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);
        setSize(600, 250);
        setLocationRelativeTo(parent);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    private ImageIcon resizeIcon(ImageIcon icon) {
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }
}