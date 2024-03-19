package Components.SearchPanel;

import javax.swing.*;
import javax.swing.border.Border;

import Components.customTable.CustomTablePanel;

import java.awt.*;
import java.awt.event.*;

public class ClearFilterButton extends JButton {
    private JTextField searchField;
    private CustomTablePanel tablePanel;

    public ClearFilterButton(JTextField searchField, CustomTablePanel tablePanel) {
        super("Limpar Filtro");
        this.searchField = searchField;
        this.tablePanel = tablePanel;

        setPreferredSize(new Dimension(120, 35));
        setFocusable(false);
        setBackground(new Color(240, 240, 240));
        setBorder(new RoundedBorder(10));

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchField.setText("");
                tablePanel.searchInTable("");
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