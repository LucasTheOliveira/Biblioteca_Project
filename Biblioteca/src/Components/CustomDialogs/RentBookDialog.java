package Components.CustomDialogs;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Components.BookTable.BookTablePanel;
import Components.Enum.Book;
import Components.Enum.CurrentUser;
import Conection.ConectionSql;

import java.awt.*;
import java.util.List;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

public class RentBookDialog extends JDialog {
    private JLabel titleLabel;
    private JLabel authorLabel;
    private JLabel categoryLabel;
    private JLabel isbnLabel;
    private JLabel rentTimeLabel;
    private JLabel rentedByLabel;
    private JLabel nameLabel;
    private JLabel cpfLabel;
    private JLabel phoneLabel;
    private JLabel rentalTimeLabel;
    private JTextField nameTextField;
    private JTextField cpfTextField;
    private JTextField phoneTextField;
    private JTextField rentalTimeTextField;
    private int selectedRow;
    private JButton saveButton;
    private BookTablePanel tablePanel;
    private List<Book> livros;
    private JTable table;
    private Book livro;

    public RentBookDialog(JFrame parent, BookTablePanel tablePanel, String title, Book livro) {
        super(parent, "", true);
        this.livro = livro;
        this.tablePanel = tablePanel;
        setLayout(new BorderLayout());
        if (livro.getStatus().equals("Alugado")) {
            setSize(850, 600);
        } else {
            setSize(850, 700);
        }
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        getContentPane().setBackground(Color.WHITE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        GridBagConstraints gbd = new GridBagConstraints();
        Font labelFont = new Font("Arial", Font.BOLD, 20);
        Font labelUserFont;
        if (livro.getStatus().equals("Disponivel")) {
            labelUserFont = new Font("Arial", Font.BOLD, 15);
        } else {
            labelUserFont = new Font("Arial", Font.BOLD, 20);
        }

        rentedByLabel = createLabel("Alugado por: ");
        titleLabel = createLabel("Título:          ");
        authorLabel = createLabel("Autor:          ");
        categoryLabel = createLabel("Categoria:          ");
        isbnLabel = createLabel("ISBN:          ");
        rentTimeLabel = createLabel("Tempo de locação máximo:          ");

        nameLabel = createLabel("Nome do Usuário: ");
        cpfLabel = createLabel("CPF: ");
        phoneLabel = createLabel("Número de Telefone: ");
        rentalTimeLabel = createLabel("Tempo de Aluguel (em dias): ");

        rentedByLabel.setFont(labelFont);
        titleLabel.setFont(labelFont);
        authorLabel.setFont(labelFont);
        categoryLabel.setFont(labelFont);
        isbnLabel.setFont(labelFont);
        rentTimeLabel.setFont(labelFont);

        nameLabel.setFont(labelUserFont);
        cpfLabel.setFont(labelUserFont);
        phoneLabel.setFont(labelUserFont);
        rentalTimeLabel.setFont(labelUserFont);

        nameTextField = new JTextField();
        cpfTextField = new FormattedText("CPF");
        phoneTextField = new FormattedText("PHONE");
        rentalTimeTextField = new JTextField();

        nameTextField.setColumns(20);
        cpfTextField.setColumns(20);
        phoneTextField.setColumns(20);
        rentalTimeTextField.setColumns(20);

        nameTextField.setBorder(new RoundedBorder(10));
        cpfTextField.setBorder(new RoundedBorder(10));
        phoneTextField.setBorder(new RoundedBorder(10));
        rentalTimeTextField.setBorder(new RoundedBorder(10));

        setPlaceholder(nameTextField, "Nome Completo");
        setPlaceholder(cpfTextField, "Digite seu Cpf");
        setPlaceholder(phoneTextField, "Telefone com DDD");
        setPlaceholder(rentalTimeTextField, "Tempo de Locação");

        JLabel dialogTitleLabel = new JLabel(livro.getStatus().equals("Alugado") ? "Devolver Livro" : "Alugar Livro");
        dialogTitleLabel.setForeground(new Color(0, 0, 139));
        dialogTitleLabel.setFont(new Font("Arial", Font.BOLD, 40));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(dialogTitleLabel, BorderLayout.WEST);

        add(titlePanel, BorderLayout.NORTH);
        add(panel, BorderLayout.WEST);

        if (livro.getStatus().equals("Alugado")) {
            gradeDevolverLivro(panel, gbc);
        } else {
            gradeAlugarLivro(panel, gbc, gbd);
        }

        GridBagConstraints titleGbc = new GridBagConstraints();
        titleGbc.gridx = 0;
        titleGbc.gridy = 0;
        titleGbc.anchor = GridBagConstraints.WEST;
        titleGbc.insets = new Insets(10, 20, 70, 0);
        panel.add(dialogTitleLabel, titleGbc);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBackground(new Color(230, 230, 230));
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusable(false);
        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancelButton.setPreferredSize(new Dimension(120, 40));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        saveButton = new JButton(livro.getStatus().equals("Alugado") ? "Devolver" : "Alugar");
        saveButton.setBackground(Color.LIGHT_GRAY);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusable(false);
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        saveButton.setPreferredSize(new Dimension(120, 40));
        saveButton.setEnabled(false);
        CurrentUser currentUser = CurrentUser.getInstance();
        String rentedBy = livro.getUsuarioAluguel();
        String username = currentUser.getUsername();
        if (livro.getStatus().equals("Alugado") && !username.equals(rentedBy) && !username.equals("admin")) {
            saveButton.setEnabled(false);
            saveButton.setBackground(Color.GRAY);
        }

        saveButton.addPropertyChangeListener("enabled", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                boolean enabled = (boolean) evt.getNewValue();
                if (!enabled) {
                    saveButton.setBackground(Color.LIGHT_GRAY);
                } else {
                    saveButton.setBackground(new Color(0, 0, 139));
                }
            }
        });

        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                enableOrDisableSaveButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                enableOrDisableSaveButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                enableOrDisableSaveButton();
            }
        };

        cpfTextField.getDocument().addDocumentListener(documentListener);
        nameTextField.getDocument().addDocumentListener(documentListener);
        phoneTextField.getDocument().addDocumentListener(documentListener);
        rentalTimeTextField.getDocument().addDocumentListener(documentListener);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CurrentUser currentUser = CurrentUser.getInstance();
                ConectionSql conexao = new ConectionSql();

                String newStatus = "";
                String username = currentUser.getUsername();
                String name = nameTextField.getText();
                String cpf = cpfTextField.getText();
                String phone = phoneTextField.getText();
                String rentaTimeUser = rentalTimeTextField.getText();

                conexao.OpenDataBase();

                if (livro.getStatus().equals("Alugado")) {
                    newStatus = "Disponivel";
                } else {
                    newStatus = "Alugado";
                }
                livro.setStatus(newStatus);

                if (newStatus.equals("Disponivel")) {
                    List<String> rentedBooks = conexao.getRentedBooks(username);
                    rentedBooks.remove(livro.getTitulo());
                    conexao.updateRentedBooks(username, rentedBooks);
                    livro.setUsuarioAluguel(null);
                    livro.setNomeUsuario(null);
                    livro.setCpfUsuario(null);
                    livro.setTelefoneUsuario(null);
                    livro.setRentTimeUser(null);
                } else {
                    List<String> rentedBooks = conexao.getRentedBooks(username);
                    rentedBooks.add(livro.getTitulo());
                    conexao.updateRentedBooks(username, rentedBooks);
                    livro.setUsuarioAluguel(username);
                    livro.setNomeUsuario(name);
                    livro.setCpfUsuario(cpf);
                    livro.setTelefoneUsuario(phone);
                    livro.setRentTimeUser(rentaTimeUser);
                }


                conexao.atualizarLivro(livro);

                try {
                    conexao.CloseDatabase();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                tablePanel.editStatus(selectedRow, newStatus);
                String successMessage = livro.getStatus().equals("Alugado")
                        ? "Livro \"" + livro.getTitulo() + "\" Alugado com sucesso!"
                        : "Livro \"" + livro.getTitulo() + "\" Devolvido com sucesso!";
                SuccessMessageDialog.showMessageDialog(
                        RentBookDialog.this,
                        successMessage,
                        "Sucesso",
                        Color.BLUE,
                        Color.WHITE,
                        Color.BLACK,
                        15);
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.setPreferredSize(new Dimension(panel.getPreferredSize().width, 100));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(buttonPanel, gbc);

        getContentPane().add(panel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void gradeAlugarLivro(JPanel panel, GridBagConstraints gbc, GridBagConstraints gbd) {
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 30, 10, 10);
        gbc.weightx = 0.8;
        panel.add(rentedByLabel, gbc);
        gbc.gridy++;
        panel.add(titleLabel, gbc);
        gbc.gridy++;
        panel.add(authorLabel, gbc);
        gbc.gridy++;
        panel.add(categoryLabel, gbc);
        gbc.gridy++;
        panel.add(isbnLabel, gbc);
        gbc.gridy++;
        panel.add(rentTimeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 0;
        panel.add(new JSeparator(SwingConstants.VERTICAL), gbc);

        gbd.gridx = 2;
        gbd.gridy = 1;
        gbc.gridheight = 2;
        gbd.anchor = GridBagConstraints.WEST;
        gbd.insets = new Insets(10, 30, 10, 20);
        gbd.weightx = 0.8;
        gbd.fill = GridBagConstraints.NONE;
        panel.add(nameLabel, gbd);
        gbd.gridy++;
        panel.add(nameTextField, gbd);
        gbd.gridy++;
        panel.add(cpfLabel, gbd);
        gbd.gridy++;
        panel.add(cpfTextField, gbd);
        gbd.gridy++;
        panel.add(phoneLabel, gbd);
        gbd.gridy++;
        panel.add(phoneTextField, gbd);
        gbd.gridy++;
        panel.add(rentalTimeLabel, gbd);
        gbd.gridy++;
        panel.add(rentalTimeTextField, gbd);
        gbd.gridy++;
    }
    
    private void gradeDevolverLivro(JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.weightx = 0.8;
        panel.add(rentedByLabel, gbc);
        gbc.gridy++;
        panel.add(titleLabel, gbc);
        gbc.gridy++;
        panel.add(authorLabel, gbc);
        gbc.gridy++;
        panel.add(categoryLabel, gbc);
        gbc.gridy++;
        panel.add(isbnLabel, gbc);
        gbc.gridy++;
        panel.add(rentTimeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 0;
        panel.add(new JSeparator(SwingConstants.VERTICAL), gbc);
    
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(15, 30, 15, 10);
        gbc.weightx = 0.8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(nameLabel, gbc);
        gbc.gridy++;
        panel.add(cpfLabel, gbc);
        gbc.gridy++;
        panel.add(phoneLabel, gbc);
        gbc.gridy++;
        panel.add(rentalTimeLabel, gbc);
        gbc.gridy++;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        return label;
    }

    private void enableOrDisableSaveButton() {
        boolean isNameEmpty = nameTextField.getText().trim().isEmpty()
                || nameTextField.getText().equals("Nome Completo");
        boolean isCpfEmpty = cpfTextField.getText().trim().isEmpty()
                || cpfTextField.getText().equals("Digite seu Cpf");
        boolean isPhoneEmpty = phoneTextField.getText().trim().isEmpty()
                || phoneTextField.getText().equals("Telefone com DDD");
        boolean isRentalTimeEmpty = phoneTextField.getText().trim().isEmpty()
                || rentalTimeTextField.getText().equals("Tempo de Locação");
        saveButton.setEnabled(!isNameEmpty && !isCpfEmpty && !isPhoneEmpty && !isRentalTimeEmpty);
    }

    public void setTitleField(String titulo) {
        titleLabel.setText("Título:   " + titulo);
        if (livro.getStatus().equals("Alugado")) {
            rentedByLabel.setText("Alugado por: " + livro.getUsuarioAluguel());
            rentedByLabel.setVisible(true);
            nameLabel.setText("Nome do Usuario:   " + livro.getNomeUsuario());
            nameLabel.setVisible(true);
            cpfLabel.setText("Cpf do Usuario:   " + livro.getCpfUsuario());
            cpfLabel.setVisible(true);
            phoneLabel.setText("Telefone do Usuario:   " + livro.getTelefoneUsuario());
            phoneLabel.setVisible(true);
            rentalTimeLabel.setText("Tempo de aluguel:   " + livro.getRentTimeUser());
            rentalTimeLabel.setVisible(true);
        } else {
            rentedByLabel.setVisible(false);
        }
    }

    public void setAuthorField(String author) {
        authorLabel.setText("Autor:   " + author);
    }

    public void setCategoryComboBox(String category) {
        categoryLabel.setText("Categoria:   " + category);
    }

    public void setIsbnField(String isbn) {
        isbnLabel.setText("ISBN:   " + isbn);
    }

    public void setRentTimeField(String rentTime) {
        rentTimeLabel.setText("Tempo de locação máximo:   " + rentTime);
    }

    public void setNomeUsuario(String nomeUsuario) {
        nameLabel.setText("Nome do Usuario:   " + nomeUsuario);
    }

    public void setCpfUsuario(String cpfUsuario) {
        cpfLabel.setText("Cpf do Usuario:   " + cpfUsuario);
    }

    public void setTelefoneUsuario(String telefoneUsuario) {
        phoneLabel.setText("Telefone do Usuario:   " + telefoneUsuario);
    }

    public void setRentTimeUser(String rentTimeUser) {
        rentalTimeLabel.setText("Telefone do Usuario:   " + rentTimeUser);
    }

    public void setSelectedRow(int selectedRow) {
        this.selectedRow = selectedRow;
    }

    public void openRentBookDialog(int row) {
        this.selectedRow = row;
        int selectedModelRow = table.convertRowIndexToModel(row);
        Book livro = livros.get(selectedModelRow);
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(table);
        RentBookDialog rentBookDialog = new RentBookDialog(parentFrame, tablePanel, livro.getTitulo(), livro);
        rentBookDialog.setIsbnField(livro.getIsbn());
        rentBookDialog.setAuthorField(livro.getAutor());
        rentBookDialog.setCategoryComboBox(livro.getCategoria());
        rentBookDialog.setRentTimeField(livro.getRentTime());
        rentBookDialog.setNomeUsuario(livro.getNomeUsuario());
        rentBookDialog.setCpfUsuario(livro.getCpfUsuario());
        rentBookDialog.setTelefoneUsuario(livro.getTelefoneUsuario());
        rentBookDialog.setRentTimeUser(livro.getRentTimeUser());
        rentBookDialog.setVisible(true);
    }

    private void setPlaceholder(JTextField field, String text) {
        field.setText(text);
        field.setForeground(Color.GRAY);
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(text)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(text);
                    field.setForeground(Color.GRAY);
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