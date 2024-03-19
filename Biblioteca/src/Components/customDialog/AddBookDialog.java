package Components.customDialog;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Components.Enum.CategoryComponent;
import Components.customTable.CustomTablePanel;
import Components.customTitle.BookListLabel;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AddBookDialog extends JDialog {
    private JTextField titleField;
    private JTextField authorField;
    @SuppressWarnings("rawtypes")
    private JComboBox categoryComboBox;
    private JButton saveButton;
    private boolean isEditing;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public AddBookDialog(JFrame parentFrame, CustomTablePanel tablePanel, String originalTitle) {
        super(parentFrame, "", true);
        this.isEditing = (originalTitle != null);
        

        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 20, 10);

        CategoryComponent categoryComponent = new CategoryComponent();
        String[] bookOptions = categoryComponent.getBookOptions();

        titleField = createTextField("Digite o nome do livro");
        authorField = createTextField("Digite o nome do autor");
        categoryComboBox = new JComboBox(bookOptions);

        titleField.setPreferredSize(new Dimension(200, titleField.getPreferredSize().height));
        authorField.setPreferredSize(new Dimension(200, authorField.getPreferredSize().height));
        categoryComboBox.setSize(200, 50);
        categoryComboBox.setBorder(new RoundedBorder(10));
        categoryComboBox.setFocusable(false);

        titleField.setFont(new Font("Arial", Font.BOLD, 15));
        authorField.setFont(new Font("Arial", Font.BOLD, 15));
        categoryComboBox.setFont(new Font("Arial", Font.BOLD, 15));
        categoryComboBox.setBackground(Color.WHITE);

        // Label "Lista de Livros"
        BookListLabel bookListLabel = new BookListLabel("");
        bookListLabel.setText(isEditing ? "Editar Livro" : "Adicionar Livro");
        GridBagConstraints bookListconstraints = bookListLabel.getConstraints(0, 0, GridBagConstraints.WEST,
                new Insets(0, 20, 80, 0));
        panel.add(bookListLabel, bookListconstraints);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 20, 50, 20);
        Font labelFont = new Font("Arial", Font.BOLD, 20);

        panel.add(createLabel("Nome do Livro:", labelFont), gbc);
        gbc.gridy++;
        panel.add(createLabel("Autor:", labelFont), gbc);
        gbc.gridy++;
        panel.add(createLabel("Categoria:", labelFont), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridwidth = 1;

        panel.add(titleField, gbc);
        gbc.gridy++;
        panel.add(authorField, gbc);
        gbc.gridy++;
        panel.add(categoryComboBox, gbc);

        saveButton = new JButton(isEditing ? "Salvar" : "Adicionar");
        JButton cancelButton = new JButton("Cancelar");

        saveButton.setBackground(Color.LIGHT_GRAY);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusable(false);
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        saveButton.setPreferredSize(new Dimension(120, 40));
        saveButton.setEnabled(false);

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

        titleField.getDocument().addDocumentListener(documentListener);
        authorField.getDocument().addDocumentListener(documentListener);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                String category = (String) categoryComboBox.getSelectedItem();
        
                if (isEditing) {
                    tablePanel.editBook(originalTitle, title, author, category);
                    SucessMessageDialog.showMessageDialog(
                            AddBookDialog.this,
                            "Livro \"" + title + "\" editado com sucesso!",
                            "Sucesso",
                            Color.BLUE,
                            Color.WHITE,
                            Color.BLACK, // Nova cor da borda
                            15);
                } else {
                    tablePanel.addBook(title, author, category);
                    SucessMessageDialog.showMessageDialog(
                            AddBookDialog.this,
                            "Livro \"" + title + "\" criado com sucesso!",
                            "Sucesso",
                            Color.GREEN,
                            Color.BLACK,
                            Color.BLACK, // Nova cor da borda
                            15);
                }
        
                dispose();
            }
        });

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

        getContentPane().add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, panel.getPreferredSize().height));

        add(panel);

        setSize(800, 600);
        setLocationRelativeTo(parentFrame);

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
    }

    public void setTitleField(String title) {
        titleField.setText(title);
    }
    
    public void setAuthorField(String author) {
        authorField.setText(author);
    }
    
    public void setCategoryComboBox(String category) {
        categoryComboBox.setSelectedItem(category);
    }

    private void enableOrDisableSaveButton() {
        boolean isTitleEmpty = titleField.getText().trim().isEmpty()
                || titleField.getText().equals("Digite o nome do livro");
        boolean isAuthorEmpty = authorField.getText().trim().isEmpty()
                || authorField.getText().equals("Digite o nome do autor");
        saveButton.setEnabled(!isTitleEmpty && !isAuthorEmpty);
    }

    private JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField();
        textField.setBorder(new RoundedBorder(10));
        setPlaceholder(textField, placeholder);
        return textField;
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
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