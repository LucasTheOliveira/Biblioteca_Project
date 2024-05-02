package loginScreen;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Components.Conexão.ConexaoMysql;

import mainScreen.MainScreen;

import java.awt.*;
import java.awt.event.*;

public class RegistrarScreen extends JFrame {
    public JTextField usernameField;
    public JPasswordField passwordField;
    @SuppressWarnings("unused")
    private MainScreen mainScreen;

    public RegistrarScreen(MainScreen mainScreen) {
        setTitle("Registrar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

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

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
                    return;
                }

                ConexaoMysql conexao = new ConexaoMysql();
                conexao.OpenDataBase();
                conexao.inserirUsuario(username, password);

                dispose();

                if(!mainScreen.isUserTableOn()) {
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
        if (mainScreen.isUserTableOn()) {
            setUndecorated(true);
            Border lineBorder = new LineBorder(Color.LIGHT_GRAY, 1);
            Border emptyBorder = new EmptyBorder(10, 10, 10, 10);
            Border compoundBorder = new CompoundBorder(lineBorder, emptyBorder);
            panel.add(cancelButton);
            panel.setBorder(compoundBorder);
            registerButton.setBounds(40, 270, 100, 30);
            cancelButton.setBounds(150, 270, 100, 30); 
            setSize(300, 350);
            titleLabel.setForeground(new Color(0, 0, 139));
        } else {
            setUndecorated(false);
            cancelButton.setBounds(100, 315, 100, 30);
            registerButton.setBounds(50, 250, 200, 30);
            setSize(300, 390);
            panel.add(loginButton);
            panel.add(separator);
        }
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

    public void setMainScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
    }
}
