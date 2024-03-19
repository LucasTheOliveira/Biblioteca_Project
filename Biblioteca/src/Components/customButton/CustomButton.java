package Components.customButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;

import Components.customDialog.AddBookDialog;
import Components.customTable.CustomTablePanel;

// CLASSE DO BOTÃO DE ADIÇÃO DE UM LIVRO
public class CustomButton extends JButton {
    private CustomTablePanel tablePanel;

// CONSTRUTOR
    public CustomButton(CustomTablePanel tablePanel) {
        super("+ Adicionar Livro");
        this.tablePanel = tablePanel;

// DEFINIÇÕES DE ESTILO DO BOTÃO DE ADIÇÃO DE UM LIVRO
        setToolTipText("Adicionar Livro");
        setFocusable(false);
        setFont(new Font("Arial", Font.BOLD, 15));
        setPreferredSize(new Dimension(250, 70));
        setBackground(new Color(0, 0, 139));
        setForeground(Color.WHITE);
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(10, 20, 10, 20));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

// LISTENER PARA DETECTAR QUANDO O BOTÃO DE CRIAÇÃO DE UM LIVRO FOR PRESSIONADO
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openAddBookDialog();
            }
        });
    }

// METODO PARA ABRIR O MODAL DE CRIAÇÃO DE UM LIVRO
    private void openAddBookDialog() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(tablePanel);
        AddBookDialog addBookDialog = new AddBookDialog(frame, tablePanel, null);
        addBookDialog.setVisible(true);
    }

// GRID QUE DETERMINA A LOCALIZAÇÃO DO BOTÃO DE ADIÇÃO DE UM LIVRO NA TELA PRINCIPAL
    public GridBagConstraints getConstraints() {
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 1;
        buttonConstraints.gridy = 1;
        buttonConstraints.anchor = GridBagConstraints.LINE_END;
        buttonConstraints.insets = new Insets(20, 20, 0, 30);
        return buttonConstraints;
    }

// METODO PARA ARREDONDAR AS BORDAS DO BOTÃO DE ADIÇÃO DE UM LIVRO
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
        g2.drawRoundRect(0, 0 , width - 1, height - 1, height, height);
        super.paintComponent(g);
    }
}