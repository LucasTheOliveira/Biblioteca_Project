package Components.SearchPanel;

import javax.swing.*;
import javax.swing.border.Border;

import Components.BookTable.BookTablePanel;
import Components.userTable.UserTable;
import Main.Main;

import java.awt.*;
import java.awt.event.*;

public class ClearFilterButton extends JButton {
    @SuppressWarnings("unused")
    private JTextField searchField;
    @SuppressWarnings("unused")
    private UserTable userTable;
    @SuppressWarnings("unused")
    private Main mainScreen;
    @SuppressWarnings("unused")
    private BookTablePanel tablePanel;

    public ClearFilterButton(JTextField searchField, BookTablePanel tablePanel, UserTable userTable, Main mainScreen) {
        super("Limpar Filtro", resizeIcon(new ImageIcon(SearchButton.class.getResource("/icons/borracha.png"))));
        this.searchField = searchField;
        this.tablePanel = tablePanel;
        this.userTable = userTable;
        this.mainScreen = mainScreen;

        setPreferredSize(new Dimension(120, 35));
        setFocusable(false);
        setBackground(new Color(240, 240, 240));
        setBorder(new RoundedBorder(10));

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchField.setText("");
                if(mainScreen.isUserTableOn()) {
                    userTable.searchInUserTable("");
                } else {
                    tablePanel.searchInTable("");
                }
            }
        });
    }

    public GridBagConstraints getConstraints() {
        GridBagConstraints searchConstraints = new GridBagConstraints();
        searchConstraints.gridx = 1;
        searchConstraints.gridy = 2;
        searchConstraints.anchor = GridBagConstraints.WEST;
        searchConstraints.insets = new Insets(10, 120, 0, 0);
        return searchConstraints;
    }

    private static ImageIcon resizeIcon(ImageIcon icon) {
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }

    private static class RoundedBorder implements Border {
        private int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}