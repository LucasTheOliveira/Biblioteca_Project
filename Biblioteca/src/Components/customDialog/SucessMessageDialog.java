package Components.customDialog;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class SucessMessageDialog extends JDialog {
    private static JDialog dialog;

    public SucessMessageDialog(Component parentComponent, Object message, String title, Color backgroundColor,
            Color textColor, Color borderColor, int fontSize) {
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

        getContentPane().add(label, BorderLayout.CENTER);
        setSize(300, 80);
        setVisible(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screenSize.width - getWidth() - 50;
        int y = 50;
        setLocation(x, y);
    }

    public static void showMessageDialog(Component parentComponent, Object message, String title, Color backgroundColor,
            Color textColor, Color borderColor, int fontSize) {
        dialog = new SucessMessageDialog(parentComponent, message, title, backgroundColor, textColor, borderColor, fontSize);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dialog.dispose();
                this.cancel();
                timer.cancel();
            }
        }, 5000);
    }

    public static Frame getRootFrame() {
        return (Frame) SwingUtilities.getWindowAncestor(dialog);
    }

    public class RoundedPane extends JPanel {

        private int radius = 20;

        public RoundedPane() {
            setOpaque(false);
            setBorder(new EmptyBorder(10, 10, 10, 10));
            setLayout(new BorderLayout());
        }

        public void setRadius(int radius) {
            this.radius = radius;
            setBorder(new EmptyBorder(radius / 2, radius / 2, radius / 2, radius / 2));
            revalidate();
            repaint();
        }

        public int getRadius() {
            return radius;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getRadius(), getRadius());
            g2.setColor(getForeground());
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getRadius(), getRadius());
            super.paintComponent(g);
        }
    }
}