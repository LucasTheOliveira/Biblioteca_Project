package Components.customDialog;

import javax.swing.*;

import Components.Enum.Livro;
import Components.customTable.CustomTablePanel;

import java.awt.*;
import java.util.List;
import java.awt.event.*;

public class RentBookDialog extends JDialog {
    private JLabel titleLabel;
    private JLabel authorLabel;
    private JLabel categoryLabel;
    private int selectedRow;
    private JLabel isbnLabel;
    private JLabel rentTimeLabel;
    private JButton saveButton;
    private CustomTablePanel tablePanel;
    private List<Livro> livros;
    private JTable table;
    @SuppressWarnings("unused")
    private Livro livro;

    public RentBookDialog(JFrame parent, CustomTablePanel tablePanel, String title, Livro livro) {
        super(parent, "", true);
        this.livro = livro;
        this.tablePanel = tablePanel;
        setLayout(new BorderLayout());
        setSize(600, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);

        getContentPane().setBackground(Color.WHITE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 20, 10);
        Font labelFont = new Font("Arial", Font.BOLD, 20);

        titleLabel = createLabel("Título:          ");
        authorLabel = createLabel("Autor:          ");
        categoryLabel = createLabel("Categoria:          ");
        isbnLabel = createLabel("ISBN:          ");
        rentTimeLabel = createLabel("Tempo de locação máximo:          ");

        titleLabel.setFont(labelFont);
        authorLabel.setFont(labelFont);
        categoryLabel.setFont(labelFont);
        isbnLabel.setFont(labelFont);
        rentTimeLabel.setFont(labelFont);

        JLabel dialogTitleLabel = new JLabel(livro.getStatus().equals("Alugado") ? "Devolver Livro" : "Alugar Livro");
        dialogTitleLabel.setForeground(new Color(0, 0, 139));
        dialogTitleLabel.setFont(new Font("Arial", Font.BOLD, 30));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(dialogTitleLabel, BorderLayout.WEST);

        add(titlePanel, BorderLayout.NORTH);
        add(panel, BorderLayout.WEST);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 20, 50, 20);

        panel.add(titleLabel, gbc);
        gbc.gridy++;
        panel.add(authorLabel, gbc);
        gbc.gridy++;
        panel.add(categoryLabel, gbc);
        gbc.gridy++;
        panel.add(isbnLabel, gbc);
        gbc.gridy++;
        panel.add(rentTimeLabel, gbc);

        setLocationRelativeTo(parent);

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
        saveButton.setBackground(new Color(0, 0, 139));
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newStatus = "";
                if (livro.getStatus().equals("Alugado")) {
                    newStatus = "Disponivel";
                } else {
                    newStatus = "Alugado";
                }
                livro.setStatus(newStatus);
                tablePanel.editStatus(selectedRow, newStatus);
                String successMessage = livro.getStatus().equals("Alugado") ? "Livro \"" + livro.getTitulo() + "\" Alugado com sucesso!" : "Livro \"" + livro.getTitulo() + "\" Devolvido com sucesso!";
                SucessMessageDialog.showMessageDialog(
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

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        return label;
    }

    public void setTitleField(String titulo) {
        titleLabel.setText("Título:   " + titulo);
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

    public void setSelectedRow(int selectedRow) {
        this.selectedRow = selectedRow;
    }

    public void openRentBookDialog(int row) {
        this.selectedRow = row;
        int selectedModelRow = table.convertRowIndexToModel(row);
        Livro livro = livros.get(selectedModelRow);
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(table);
        RentBookDialog rentBookDialog = new RentBookDialog(parentFrame, tablePanel, livro.getTitulo(), livro);
        rentBookDialog.setIsbnField(livro.getIsbn());
        rentBookDialog.setAuthorField(livro.getAutor());
        rentBookDialog.setCategoryComboBox(livro.getCategoria());
        rentBookDialog.setRentTimeField(livro.getRentTime());
        rentBookDialog.setVisible(true);
    }
}