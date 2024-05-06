package Components.SearchPanel;

import javax.swing.*;
import javax.swing.border.Border;

import Components.customTable.CustomTablePanel;
import Components.userTable.UserTable;
import mainScreen.MainScreen;

import java.awt.*;
import java.awt.event.*;

public class SearchButton extends JButton {
    @SuppressWarnings("unused")
    private CustomTablePanel tablePanel;
    @SuppressWarnings("unused")
    private JTextField searchField;
    @SuppressWarnings("unused")
    private UserTable userTable;
    @SuppressWarnings("unused")
    private MainScreen mainScreen;

    public SearchButton(JTextField searchField, CustomTablePanel tablePanel, UserTable userTable, MainScreen mainScreen) {
        super("Pesquisar", resizeIcon(new ImageIcon(SearchButton.class.getResource("/icons/search_icon.png"))));
        this.searchField = searchField;
        this.tablePanel = tablePanel;
        this.userTable = userTable;
        this.mainScreen = mainScreen;

        setFocusable(false);
        setPreferredSize(new Dimension(100, 35));
        setBackground(new Color(240, 240, 240));
        setBorder(new RoundedBorder(10));

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText();
                if(mainScreen.isUserTableOn()) {
                    userTable.searchInUserTable(searchTerm);
                } else {
                    tablePanel.searchInTable(searchTerm);
                }
            }
        });
    }

    public GridBagConstraints getConstraints() {
        GridBagConstraints searchButtonConstraints = new GridBagConstraints();
        searchButtonConstraints.gridx = 1;
        searchButtonConstraints.gridy = 2;
        searchButtonConstraints.anchor = GridBagConstraints.WEST;
        searchButtonConstraints.insets = new Insets(10, 10, 0, 0);
        return searchButtonConstraints;
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