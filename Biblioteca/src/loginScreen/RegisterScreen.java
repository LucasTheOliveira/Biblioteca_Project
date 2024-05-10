package loginScreen;

import javax.swing.*;
import javax.swing.border.Border;
import Components.Enum.User;
import Main.Main;

import java.util.List;

import Components.Enum.UserType;
import Conection.ConectionSql;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class RegisterScreen extends JFrame {
    public JTextField usernameField;
    public JPasswordField passwordField;
    private JCheckBox showPasswordCheckBox;
    @SuppressWarnings("unused")
    private Main mainScreen;
    private List<User> usuarios;

    public RegisterScreen(Main mainScreen, List<User> usuarios) {
        setTitle("Registrar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        this.usuarios = usuarios != null ? usuarios : new ArrayList<>();
        this.mainScreen = mainScreen;

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        getContentPane().add(panel);
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Registrar");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(20, 20, 250, 45);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);

        JLabel userLabel = new JLabel("Usuário:");
        userLabel.setBounds(25, 90, 100, 30);
        userLabel.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(25, 120, 250, 35);
        usernameField.setBorder(new RoundedBorder(10));
        setPlaceholder(usernameField, "Usuário");
        panel.add(usernameField);

        showPasswordCheckBox = new JCheckBox(resizeIcon(new ImageIcon(getClass().getResource("/icons/olho.png"))));
        showPasswordCheckBox.setBounds(280, 195, 30, 25);
        showPasswordCheckBox.setBackground(Color.WHITE);
        showPasswordCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (showPasswordCheckBox.isSelected()) {
                    passwordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('*');
                }
                if (showPasswordCheckBox.isSelected()) {
                    showPasswordCheckBox
                            .setIcon(resizeIcon(new ImageIcon(getClass().getResource("/icons/invisivel.png"))));
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

        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setBounds(25, 160, 100, 30);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(25, 190, 250, 35);
        passwordField.setBorder(new RoundedBorder(10));
        passwordField.setEchoChar((char) 0);
        setPlaceholder(passwordField, "Senha");
        panel.add(passwordField);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 300, 300, 10);
        separator.setForeground(new Color(180, 180, 180));

        int larguraJanela = 300;
        int alturaJanela = mainScreen.isUserTableOn() ? 350 : 390;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int larguraTela = (int) screenSize.getWidth();
        int alturaTela = (int) screenSize.getHeight();
        int posX = (larguraTela - larguraJanela) / 2;
        int posY = (alturaTela - alturaJanela) / 2;
        setLocation(posX, posY);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBackground(new Color(230, 230, 230));
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 15));
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

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 315, 100, 30);
        loginButton.setBackground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginScreen loginScreen = new LoginScreen(mainScreen);
                loginScreen.setVisible(true);
                dispose();
            }
        });

        JButton registerButton = new JButton("Registrar");
        registerButton.setBackground(new Color(0, 0, 139));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 15));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                UserType tipo = UserType.COMUM;
                int nextId = getNextID();
                List<String> rentedBooks = new ArrayList<>();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
                    return;
                }

                User newUser = new User(nextId, username, password, tipo, rentedBooks);

                ConectionSql conexao = new ConectionSql();
                conexao.OpenDataBase();
                conexao.inserirUsuario(newUser);

                dispose();

                if (!mainScreen.isUserTableOn()) {
                    LoginScreen loginScreen = new LoginScreen(mainScreen);
                    loginScreen.setVisible(true);
                }
            }
        });
        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });
        setUndecorated(false);
        cancelButton.setBounds(100, 315, 100, 30);
        registerButton.setBounds(50, 250, 200, 30);
        setSize(320, 390);
        panel.add(loginButton);
        panel.add(separator);
        panel.add(registerButton);
    }

    private int getNextID() {
        int maxID = 0;
        for (User usuario : usuarios) {
            if (usuario.getId() > maxID) {
                maxID = usuario.getId();
            }
        }
        return maxID + 1;
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

    public void setMainScreen(Main mainScreen) {
        this.mainScreen = mainScreen;
    }
}
