package Components.customTitle;


import javax.swing.*;
import java.awt.*;

// CLASSE DO LABEL NO TOPO DA TELA (DEIXEI CUSTOMIZAVEL PARA QUE POSSA SER REUTILIZADO EM OUTROS LUGARES OU COM OUTROS TEXTOS)
// EXEMPLO: 
// PARA ADMINS = LISTA DE LIVROS (ADMIN) 
// PARA USUARIOS COMUMS = LISTA DE LIVROS 
public class BookListLabel extends JLabel {
    public BookListLabel(String text) {
        super(text);

// DEFINE AS PROPRIEDADES DO LABEL
        setFont(new Font("Arial", Font.BOLD, 40));
        setForeground(new Color(0, 0, 139));
    }

// DEFINE A LOCALIZAÇÃO DO LABEL
    public GridBagConstraints getConstraints(int gridx, int gridy, int anchor, Insets insets) {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = gridx;
        labelConstraints.gridy = gridy;
        labelConstraints.anchor = anchor;
        labelConstraints.insets = insets;
        return labelConstraints;
    }

// METODO PARA DEFINIR O TEXTO DO LABEL
    public void setText(String text) {
        super.setText(text);
    }
}
