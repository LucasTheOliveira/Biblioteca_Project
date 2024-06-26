package Components.SearchPanel;

import javax.swing.*;
import javax.swing.border.Border;

import Components.BookTable.BookTablePanel;
import Components.userTable.UserTable;
import Main.Main;

import java.awt.*;
import java.awt.event.*;

public class SearchField extends JTextField {
    private BookTablePanel tablePanel;
    private UserTable userTable;
    @SuppressWarnings("unused")
    private Main mainScreen;

    public SearchField(Main mainScreen) {
        super(FlowLayout.LEFT);
        setPlaceholder("Digite aqui para pesquisar...");
        setPreferredSize(new Dimension(400, 50));
        setBorder(new RoundedBorder(10));
        setBackground(Color.WHITE);
        this.mainScreen = mainScreen;

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = getText();
                if(mainScreen.isUserTableOn()) {
                    userTable.searchInUserTable(searchTerm);
                } else {                    
                    tablePanel.searchInTable(searchTerm);
                }
            }
        });
    }

    public GridBagConstraints getConstraints() {
        GridBagConstraints searchConstraints = new GridBagConstraints();
        searchConstraints.gridx = 0;
        searchConstraints.gridy = 2;
        searchConstraints.anchor = GridBagConstraints.WEST;
        searchConstraints.insets = new Insets(10, 20, 0, 0);
        return searchConstraints;
    }

    private void setPlaceholder(String text) {
        setText(text);
        setForeground(Color.GRAY);
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(text)) {
                    setText("");
                    setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(text);
                    setForeground(Color.GRAY);
                }
            }
        });
    }

    private class RoundedBorder implements Border {
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