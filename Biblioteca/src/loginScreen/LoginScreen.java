package loginScreen;

import mainScreen.MainScreen;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import Components.Conexão.ConexaoMysql;
import java.awt.*;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginScreen() {
        setTitle("Login");
        setSize(300, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(20, 20, 250, 30);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);

        JLabel userLabel = new JLabel("Usuário:");
        userLabel.setBounds(20, 90, 100, 30);
        userLabel.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(20, 120, 250, 30);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setBounds(20, 160, 100, 30);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(20, 190, 250, 30);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(40, 250, 100, 30);
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
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Autenticar no banco de dados
                ConexaoMysql conexao = new ConexaoMysql();
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
                        new MainScreen(isAdmin).setVisible(true);
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
        registerButton.setBounds(150, 250, 100, 30);
        registerButton.setBackground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 15));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                RegistrarScreen registrarScreen = new RegistrarScreen();
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

    public static void main(String[] args) {
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.setVisible(true);
    }
}