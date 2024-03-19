package Components.customDialog;

import javax.swing.*;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

// CLASSE DO ALERTA DE SUCESSO 
public class SucessMessageDialog extends JDialog {
    private static JDialog dialog;

    public SucessMessageDialog(Component parentComponent, Object message, String title, Color backgroundColor, Color textColor, Color borderColor, int fontSize) {
        super((Frame) null, title, false);
// DEFINE QUE O ALERTA SEMPRE DEVE APARECER NA FRENTE DE TODOS OS OUTROS ELEMNTOS
        setAlwaysOnTop(true);

        setUndecorated(true);
        getContentPane().setLayout(new BorderLayout());

// DEFINIÇÃO DO LABEL E ESTILO DO ALERTA
        JLabel label = new JLabel(message.toString());
        label.setOpaque(true);
        label.setBackground(backgroundColor);
        label.setForeground(textColor);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, fontSize));

        label.setBorder(BorderFactory.createLineBorder(borderColor));

        getContentPane().add(label, BorderLayout.CENTER);

// DEFINIÇÃO DO TAMANHO DO ALERTA
        setSize(300, 80);

// DEFINIÇÃO DA LOCALIZAÇÃO QUE O ALERTA DEVE APARECER
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screenSize.width - getWidth() - 50;
        int y = 50;
        setLocation(x, y);

        setVisible(true);
    }

// METODO PARA MOSTRAR O ALERTA
    public static void showMessageDialog(Component parentComponent, Object message, String title, Color backgroundColor, Color textColor, Color borderColor, int fontSize) {

// DIALOG É O ALERTA COM TODAS AS SUAS PROPRIEDADES, ELAS DEVEM SER DEFINIDAS NO COMPONENTE QUE FOR CHAMADA (TITULO, COR, ETC)
        dialog = new SucessMessageDialog(parentComponent, message, title, backgroundColor, textColor, borderColor, fontSize);

// TIMER PARA QUE O MODAL SE FECHE SOZINHO DEPOIS DE 5 SEGUNDOS
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dialog.dispose();
                timer.cancel();
            }
        }, 5000);
    }

// METODO PARA PEGAR O FRAME DO COMPONENTE PRINCIPAL USADO NA DEFINIÇÃO DA LOCALIZAÇÃO QUE O ALERTA DEVE APARECER 
    public static Frame getRootFrame() {
        return (Frame) SwingUtilities.getWindowAncestor(dialog);
    }
}