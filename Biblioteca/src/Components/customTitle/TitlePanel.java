package Components.customTitle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Components.CustomDialogs.AddUserDialog;
import Components.CustomDialogs.CustomDeleteConfirmationDialog;
import Components.userTable.UserTable;
import Main.Main;
import loginScreen.LoginScreen;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitlePanel extends JPanel {
    @SuppressWarnings("unused")
    private Main mainScreen;
    private JButton userListButton;
    private JButton logoutButton;
    private JButton addUserButton;
    private UserTable userTable;
    private Boolean userEdit;

    public TitlePanel(JFrame parentFrame, boolean isAdmin, Main mainScreen, UserTable userTable, boolean userEdit) {
        setLayout(new BorderLayout());
        setBackground(new Color(0, 120, 215));
        setPreferredSize(new Dimension(getPreferredSize().width, 40));

        this.mainScreen = mainScreen;
        this.userTable = userTable;
        this.userEdit = userEdit;

        JLabel titleLabel = new JLabel("Biblioteca UNAERP");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(titleLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        logoutButton = new JButton(resizeIcon(new ImageIcon(getClass().getResource("/icons/logout.png"))));
        logoutButton.setBackground(Color.WHITE);
        logoutButton.setFocusable(false);
        logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutButton.setToolTipText("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomDeleteConfirmationDialog confirmationDialog = new CustomDeleteConfirmationDialog(null,
                        "Tem certeza que deseja deslogar?",
                        new ImageIcon(getClass().getResource("/icons/logout.png")));

                confirmationDialog.setVisible(true);

                if (confirmationDialog.isConfirmed()) {
                    parentFrame.dispose();
                    LoginScreen loginScreen = new LoginScreen(mainScreen);
                    loginScreen.setVisible(true);
                }
            }
        });

        if (isAdmin) {
            addUserButton = new JButton(resizeIcon(new ImageIcon(getClass().getResource("/icons/edit_user.png"))));
            addUserButton.setBackground(Color.WHITE);
            addUserButton.setFocusable(false);
            addUserButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addUserButton.setToolTipText("Adicionar Usuário");
            addUserButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AddUserDialog addUserDialog = new AddUserDialog(parentFrame, userTable, null, TitlePanel.this);
                    addUserDialog.setVisible(true);
                }
            });

            userListButton = new JButton(resizeIcon(new ImageIcon(getClass().getResource(mainScreen.isUserTableOn() ? "/icons/user.png" : "/icons/livro.png"))));
            userListButton.setBackground(Color.WHITE);
            userListButton.setFocusable(false);
            userListButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            userListButton.setToolTipText("Lista de Usuários");
            userListButton.setIcon(resizeIcon(new ImageIcon(getClass().getResource("/icons/user.png"))));
            userListButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!mainScreen.isUserTableOn()) {
                        mainScreen.showUserTable();
                        userListButton.setIcon(resizeIcon(new ImageIcon(getClass().getResource("/icons/livro.png"))));
                    } else {
                        mainScreen.showBookTable();
                        userListButton.setIcon(resizeIcon(new ImageIcon(getClass().getResource("/icons/user.png"))));
                    }
                }
            });

            buttonPanel.add(addUserButton);
            buttonPanel.add(userListButton);
        }

        buttonPanel.add(logoutButton);
        buttonPanel.setBorder(new EmptyBorder(0, 0, 0, 80));

        add(buttonPanel, BorderLayout.EAST);

        if (isAdmin) {
            JPanel leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            leftButtonPanel.setOpaque(false);
            leftButtonPanel.add(addUserButton);
            leftButtonPanel.setBorder(new EmptyBorder(0, 0, 0, 10));
            add(leftButtonPanel, BorderLayout.WEST);
        }
    }

    public Boolean getUserEdit() {
        return userEdit;
    }

    public GridBagConstraints getConstraints() {
        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 0;
        titleConstraints.gridwidth = 2;
        titleConstraints.fill = GridBagConstraints.HORIZONTAL;
        return titleConstraints;
    }

    private ImageIcon resizeIcon(ImageIcon icon) {
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(22, 22, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }
}