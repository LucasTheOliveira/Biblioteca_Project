package Components.customDialog;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

// CLASSE DO MODAL DE CONFIRMAÇÃO DE DELEÇÃO
public class CustomDeleteConfirmationDialog extends JDialog {
    private boolean confirmed = false;

    public CustomDeleteConfirmationDialog(JFrame parent, String bookName) {
        super(parent, "", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setUndecorated(true);

        getContentPane().setBackground(Color.WHITE);

// AJUSTE E DEFINIÇÃO DO ICONI DE LIXEIRA (ICONI PEGO DA PASTA "ICONS")
        ImageIcon trashIcon = resizeIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
        JLabel iconLabel = new JLabel(trashIcon);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

// DEFINIÇÃO DO TEXTO DE CONFIRMAÇÃO DE DELEÇÃO
        JLabel label = new JLabel("Tem certeza de que deseja deletar o livro \"" + bookName + "\"?");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));

// DEFINIÇÃO DO PAINEL DO ICONI DE LIXEIRA
        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        iconPanel.setBackground(Color.WHITE);
        iconPanel.add(iconLabel);

// DEFINIÇÃO DO BOTÃO DE CONFIRMAÇÃO DE DELEÇÃO
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

// DEFINIÇÃO DO BOTÃO DE CANCELAR DELEÇÃO
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

// DEFINIÇÃO DO PAINEL DOS BOTÕES
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        contentPane.setBackground(Color.WHITE);
        contentPane.add(iconPanel, BorderLayout.NORTH);
        contentPane.add(label, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

// DEFINIÇÃO DO TAMANHO DO MODAL E LOCALIZAÇÃO RELATIVA AO PAINEL PRINCIPAL
        setContentPane(contentPane);
        setSize(600, 250);
        setLocationRelativeTo(parent);
    }

// METODO BOLEANO PARA INDETIFICAR CLIQUE NO BOTÃO DE CONFIRMAÇÃO ( NESCESSARIO???? )
    public boolean isConfirmed() {
        return confirmed;
    }

// METODO PARA MANIPULZAÇÃO DO TAMANHO DO ICONI
    private ImageIcon resizeIcon(ImageIcon icon) {
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }
}