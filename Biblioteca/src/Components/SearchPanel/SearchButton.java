package Components.SearchPanel;

import javax.swing.*;
import javax.swing.border.Border;

import Components.customTable.CustomTablePanel;

import java.awt.*;
import java.awt.event.*;

// CLASSE DO BOTÃO DE SEARCH
public class SearchButton extends JButton {
    private CustomTablePanel tablePanel;
    private JTextField searchField;

    public SearchButton(JTextField searchField, CustomTablePanel tablePanel) {
        super("Pesquisar", resizeIcon(new ImageIcon(SearchButton.class.getResource("/icons/search_icon.png"))));

// INSTANCIA O CAMPO DE BUSCA O PAINEL DA TABELA
        this.searchField = searchField;
        this.tablePanel = tablePanel;

// ADICIONA UM LISTENER PARA DETECTAR QUANDO O BOTÃO FOR APERTADO
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText();
                tablePanel.searchInTable(searchTerm);
            }
        });

// DEFINE AS PROPRIEDADES DO BOTÃO
        setFocusable(false);
        setPreferredSize(new Dimension(100, 35));
        setBackground(new Color(240, 240, 240));
        setBorder(new RoundedBorder(10));
    }

// METODO PARA MANIPULZAÇÃO DO TAMANHO DO ICONI
    private static ImageIcon resizeIcon(ImageIcon icon) {
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }

// DEFINE A CONFIGURAÇÃO/LOCALIZAÇÃO DE LAYOUT DO BOTÃO
    public GridBagConstraints getConstraints() {
        GridBagConstraints searchButtonConstraints = new GridBagConstraints();
        searchButtonConstraints.gridx = 1;
        searchButtonConstraints.gridy = 2;
        searchButtonConstraints.anchor = GridBagConstraints.WEST;
        searchButtonConstraints.insets = new Insets(10, 10, 0, 0);
        return searchButtonConstraints;
    }

// CLASSE PARA DEIXAR A BORDA DO BOTÃO ARREDONDADA
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