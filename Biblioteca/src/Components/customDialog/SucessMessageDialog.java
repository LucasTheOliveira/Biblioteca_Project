package Components.customDialog;

import javax.swing.*;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class SucessMessageDialog extends JDialog {
    private static JDialog dialog;

    public SucessMessageDialog(Component parentComponent, Object message, String title, Color backgroundColor, Color textColor, Color borderColor, int fontSize) {
        super((Frame) null, title, false);
        setAlwaysOnTop(true);
        setUndecorated(true);
        getContentPane().setLayout(new BorderLayout());

        JLabel label = new JLabel(message.toString());
        label.setOpaque(true);
        label.setBackground(backgroundColor);
        label.setForeground(textColor);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, fontSize));
        label.setBorder(BorderFactory.createLineBorder(borderColor));

        getContentPane().add(label, BorderLayout.CENTER);
        setSize(300, 80);
        setVisible(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screenSize.width - getWidth() - 50;
        int y = 50;
        setLocation(x, y);
    }

    public static void showMessageDialog(Component parentComponent, Object message, String title, Color backgroundColor, Color textColor, Color borderColor, int fontSize) {
        dialog = new SucessMessageDialog(parentComponent, message, title, backgroundColor, textColor, borderColor, fontSize);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dialog.dispose();
                timer.cancel();
            }
        }, 5000);
    }

    public static Frame getRootFrame() {
        return (Frame) SwingUtilities.getWindowAncestor(dialog);
    }
}