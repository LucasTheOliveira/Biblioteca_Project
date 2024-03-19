package Components.customTitle;

import javax.swing.*;
import java.awt.*;

public class BookListLabel extends JLabel {
    public BookListLabel(String text) {
        super(text);

        setFont(new Font("Arial", Font.BOLD, 40));
        setForeground(new Color(0, 0, 139));
    }

    public GridBagConstraints getConstraints(int gridx, int gridy, int anchor, Insets insets) {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = gridx;
        labelConstraints.gridy = gridy;
        labelConstraints.anchor = anchor;
        labelConstraints.insets = insets;
        return labelConstraints;
    }

    public void setText(String text) {
        super.setText(text);
    }
}
