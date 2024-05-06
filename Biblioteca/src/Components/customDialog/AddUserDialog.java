package Components.customDialog;

import javax.swing.*;
import javax.swing.border.Border;
import Components.userTable.UserTable;
import Components.Enum.UserType;

import java.util.ArrayList;
import java.util.List;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class AddUserDialog extends JDialog {
    public JTextField usernameField;
    public JPasswordField passwordField;
    private JComboBox<UserType> typeComboBox;
    private JCheckBox showPasswordCheckBox;
    public UserTable userTablePanel;
    public Boolean isEditing;

    public AddUserDialog(JFrame parentFrame, UserTable userTablePanel, String originalName) {
        super(parentFrame, "", true);
        this.isEditing = (originalName != null);
        this.userTablePanel = userTablePanel;

        setTitle(isEditing ? "Editar Usuario" : "Registrar");
        setLayout(new BorderLayout());
        setResizable(false);
        setUndecorated(true);
        setSize(420, 480);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int dialogWidth = getWidth();
        int dialogHeight = getHeight();
        int x = (screenWidth - dialogWidth) / 2;
        int y = (screenHeight - dialogHeight) / 2;

        setLocation(x, y);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel.setBackground(Color.WHITE);
        getContentPane().add(panel);
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Registrar");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setBounds(50, 20, 300, 60);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(0, 0, 139));
        panel.add(titleLabel);

        JLabel userLabel = new JLabel("Usuário");
        userLabel.setBounds(25, 90, 100, 30);
        userLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(25, 130, 350, 35);
        usernameField.setBorder(new RoundedBorder(10));
        setPlaceholder(usernameField, "Usuário");
        panel.add(usernameField);

        showPasswordCheckBox = new JCheckBox(resizeIcon(new ImageIcon(getClass().getResource("/icons/olho.png"))));
        showPasswordCheckBox.setBounds(380, 225, 30, 25);
        showPasswordCheckBox.setBackground(Color.WHITE);
        showPasswordCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (showPasswordCheckBox.isSelected()) {
                    passwordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('*');
                }
                if (showPasswordCheckBox.isSelected()) {
                    showPasswordCheckBox.setIcon(resizeIcon(new ImageIcon(getClass().getResource("/icons/invisivel.png"))));
                } else {
                    showPasswordCheckBox.setIcon(resizeIcon(new ImageIcon(getClass().getResource("/icons/olho.png"))));
                }
            }
        });
        
        showPasswordCheckBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                showPasswordCheckBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                showPasswordCheckBox.setCursor(Cursor.getDefaultCursor());
            }
        });
        panel.add(showPasswordCheckBox);

        JLabel passwordLabel = new JLabel("Senha");
        passwordLabel.setBounds(25, 180, 100, 30);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(passwordLabel);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(25, 220, 350, 35);
        passwordField.setBorder(new RoundedBorder(10));
        passwordField.setEchoChar('*');
        setPlaceholder(passwordField, "Senha");
        panel.add(passwordField);

        JLabel typeLabel = new JLabel("Tipo");
        typeLabel.setBounds(25, 270, 100, 30);
        typeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(typeLabel);

        typeComboBox = new JComboBox();
        typeComboBox.setBounds(25, 310, 350, 45);
        typeComboBox.setBorder(new RoundedBorder(10));
        typeComboBox.setFocusable(false);
        typeComboBox.setFont(new Font("Arial", Font.BOLD, 15));
        typeComboBox.setBackground(Color.WHITE);
        panel.add(typeComboBox);
        for (UserType userType : UserType.values()) {
            typeComboBox.addItem(userType);
        }

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBackground(new Color(230, 230, 230));
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 20));
        cancelButton.setBounds(220, 400, 150, 40);
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JButton registerButton = new JButton(isEditing ? "Salvar" : "Registrar");
        new JButton(isEditing ? "Salvar" : "Adicionar");
        registerButton.setBackground(new Color(0, 0, 139));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 20));
        registerButton.setBounds(40, 400, 150, 40);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String usuario = usernameField.getText();
                    String senha = new String(passwordField.getPassword());
                    UserType tipo = (UserType) typeComboBox.getSelectedItem();
                    List<String> rentedBooks = new ArrayList<>();

                    if (isEditing) {
                        userTablePanel.editUser(originalName, usuario, senha, tipo, rentedBooks);
                        SucessMessageDialog.showMessageDialog(
                                AddUserDialog.this,
                                "Usuario \"" + usuario + "\" editado com sucesso!",
                                "Sucesso",
                                Color.BLUE,
                                Color.WHITE,
                                Color.BLACK,
                                15);
                        dispose();
                    } else {
                        userTablePanel.addUser(usuario, senha, tipo, rentedBooks);
                        SucessMessageDialog.showMessageDialog(
                                AddUserDialog.this,
                                "Usuario \"" + usuario + "\" criado com sucesso!",
                                "Sucesso",
                                Color.GREEN,
                                Color.BLACK,
                                Color.BLACK,
                                15);
                        dispose();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });

        panel.add(cancelButton);
        panel.add(registerButton);
    }

    public void setUsername(String username) {
        usernameField.setText(username);
        usernameField.setForeground(Color.BLACK);
    }

    public void setPassword(String password) {
        passwordField.setText(password);
        passwordField.setForeground(Color.BLACK);
        passwordField.setEchoChar('*');
    }

    public void selectUserType(UserType userType) {
        typeComboBox.setSelectedItem(userType);
    }

    private ImageIcon resizeIcon(ImageIcon icon) {
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }

    private void setPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                    if (textField == passwordField) {
                        passwordField.setEchoChar('*');
                    }
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                    if (textField == passwordField) {
                        passwordField.setEchoChar((char) 0);
                    }
                }
            }
        });
    }

    private class RoundedBorder implements Border {
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