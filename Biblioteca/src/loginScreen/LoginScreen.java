package loginScreen;

import javax.swing.*;
import javax.swing.border.Border;

import Components.Enum.CurrentUser;
import Conection.ConectionSql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Main.Main;

import java.awt.*;
import java.awt.event.*;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckBox;
    @SuppressWarnings("unused")
    private Main mainScreen;
    @SuppressWarnings("unused")
    private boolean admin;

    public LoginScreen(Main mainScreen) {
        setTitle("Login");
        setSize(320, 390);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        this.mainScreen = mainScreen;

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        getContentPane().add(panel);
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Login");
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
        panel.add(separator);

        int larguraJanela = 300;
        int alturaJanela = 390;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int larguraTela = (int) screenSize.getWidth();
        int alturaTela = (int) screenSize.getHeight();
        int posX = (larguraTela - larguraJanela) / 2;
        int posY = (alturaTela - alturaJanela) / 2;
        setLocation(posX, posY);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 250, 200, 30);
        loginButton.setBackground(new Color(0, 0, 139));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        loginButton.setHorizontalAlignment(SwingConstants.CENTER);
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                ConectionSql conexao = new ConectionSql();
                conexao.OpenDataBase();
                String sql = "SELECT * FROM usuarios WHERE nome = '" + username + "' AND senha = '" + password + "'";
                ResultSet resultSet = conexao.ExecutaQuery(sql);

                try {
                    if (resultSet.next()) {
                        String userType = resultSet.getString("tipo");
                        boolean isAdmin = userType.equals("admin");
                        CurrentUser currentUser = CurrentUser.getInstance();
                        currentUser.setUsername(username);
                        currentUser.setAdmin(isAdmin);
                        new Main(isAdmin).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        conexao.CloseDatabase();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        panel.add(loginButton);

        JButton registerButton = new JButton("Registrar");
        registerButton.setBounds(100, 315, 100, 30);
        registerButton.setBackground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 15));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                RegisterScreen registrarScreen = new RegisterScreen(mainScreen, new ArrayList<>());
                registrarScreen.setVisible(true);
            }
        });
        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });
        panel.add(registerButton);
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
    public static void main(String[] args) {
        Boolean admin = true;
        Main mainScreen = new Main(admin);
        LoginScreen loginScreen = new LoginScreen(mainScreen);
        loginScreen.setVisible(true);
    }
}