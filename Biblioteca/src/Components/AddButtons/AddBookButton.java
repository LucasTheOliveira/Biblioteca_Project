package Components.AddButtons;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;

import Components.BookTable.BookTablePanel;
import Components.CustomDialogs.AddBookDialog;
import Main.Main;

public class AddBookButton extends JButton {
    @SuppressWarnings("unused")
    private BookTablePanel tablePanel;
    @SuppressWarnings("unused")
    private Main mainScreen;
    
    public AddBookButton(BookTablePanel tablePanel, Main mainScreen) {
        super("+ Adicionar Livro");
        this.tablePanel = tablePanel;
        this.mainScreen = mainScreen;

        setFocusable(false);
        setFont(new Font("Arial", Font.BOLD, 15));
        setPreferredSize(new Dimension(250, 70));
        setBackground(new Color(0, 0, 139));
        setForeground(Color.WHITE);
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(10, 20, 10, 20));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setToolTipText("Adicionar Livro");

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(tablePanel);
                AddBookDialog addBookDialog = new AddBookDialog(frame, tablePanel, null);
                addBookDialog.setVisible(true);
            }
        });
    }

    public GridBagConstraints getConstraints() {
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 1;
        buttonConstraints.gridy = 1;
        buttonConstraints.anchor = GridBagConstraints.LINE_END;
        buttonConstraints.insets = new Insets(20, 20, 0, 30);
        return buttonConstraints;
    }

    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isPressed()) {
            g2.setColor(new Color(0, 0, 250));
        } else if (getModel().isRollover()) {
            g2.setColor(new Color(0, 0, 200));
        } else {
            g2.setColor(getBackground());
        }
        g2.fillRoundRect(0, 0, width - 1, height - 1, height, height);
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(0, 0, width - 1, height - 1, height, height);
        super.paintComponent(g);
    }
}