package Components.CustomDialogs;

import javax.swing.JTextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FormattedText extends JTextField {
    private final int CPF_LENGTH = 11;
    private final int PHONE_LENGTH = 11;

    public FormattedText(String type) {
        super();
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                formatText(type);
            }
        });
    }

    private void formatText(String type) {
        String text = this.getText().replaceAll("[^\\d]", "");
        StringBuilder formattedText = new StringBuilder();

        int maxLength = (type.equals("CPF")) ? CPF_LENGTH : PHONE_LENGTH;

        for (int i = 0; i < text.length(); i++) {
            if (i == maxLength) {
                break;
            }
            if (type.equals("CPF") && (i == 3 || i == 6)) {
                formattedText.append('.');
            }
            if (type.equals("CPF") && i == 9) {
                formattedText.append('-');
            }
            if (type.equals("PHONE") && i == 0) {
                formattedText.append('(');
            }
            if (type.equals("PHONE") && i == 2) {
                formattedText.append(") ");
            }
            if (type.equals("PHONE") && i == 7) {
                formattedText.append('-');
            }
            formattedText.append(text.charAt(i));
        }

        this.setText(formattedText.toString());
    }
}