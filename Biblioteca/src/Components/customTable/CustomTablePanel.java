package Components.customTable;

import javax.swing.*;
import javax.swing.table.*;

import Components.Enum.Livro;
import Components.customDialog.AddBookDialog;
import Components.customDialog.CustomDeleteConfirmationDialog;
import Components.customDialog.RentBookDialog;
import Components.customDialog.SucessMessageDialog;
import loginScreen.LoginScreen;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

// CLASSE DO PAINEL DA TABELA
public class CustomTablePanel extends JPanel {
    private JTable table;
    private CustomTableModel model;
    private List<Livro> livros;
    private boolean admin;

    public CustomTablePanel() {
        LoginScreen loginScreen = new LoginScreen();
        this.admin = loginScreen.isAdmin();

        TableCellRenderer statusRenderer = new StatusRenderer();

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 400));

        livros = new ArrayList<>();
        livros.add(new Livro(1, "9788573260712", "Dom Casmurro", "Machado de Assis", "Romance", "Disponivel", "1 Semana"));
        livros.add(new Livro(2, "9788532530788", "Harry Potter e a Pedra Filosofal", "J.K. Rowling", "Fantasia", "Alugado", "1 Semana"));
        livros.add(new Livro(3, "9788535914849", "1984", "George Orwell", "Ficção Científica", "Disponivel", "1 Semana"));
        livros.add(new Livro(4, "9788595081267", "O Senhor dos Anéis: A Sociedade do Anel", "J.R.R. Tolkien", "Fantasia", "Disponivel", "1 Semana"));
        livros.add(new Livro(5, "9788573262303", "Cem Anos de Solidão", "Gabriel García Márquez", "Realismo Mágico", "Alugado", "1 Semana"));
        livros.add(new Livro(6, "9788580572182", "A Culpa É das Estrelas", "John Green", "Romance", "Disponivel", "1 Semana"));
        livros.add(new Livro(7, "9788582850372", "O Pequeno Príncipe", "Antoine de Saint-Exupéry", "Literatura Infantil", "Disponivel", "1 Semana"));
        livros.add(new Livro(8, "9788598078170", "Percy Jackson e o Ladrão de Raios", "Rick Riordan", "Fantasia", "Disponivel", "1 Semana"));
        livros.add(new Livro(9, "9788539004111", "O Poder do Hábito", "Charles Duhigg", "Autoajuda", "Disponivel", "1 Semana"));
        livros.add(new Livro(10, "9788535915631", "A Revolução dos Bichos", "George Orwell", "Sátira Política", "Alugado", "1 Semana"));
        livros.add(new Livro(11, "9788580411249", "O Alquimista", "Paulo Coelho", "Ficção Espiritual", "Disponivel", "1 Semana"));
        livros.add(new Livro(12, "9788578270695", "As Crônicas de Nárnia: O Leão, a Feiticeira e o Guarda-Roupa", "C.S. Lewis", "Fantasia", "Alugado", "1 Semana"));
        livros.add(new Livro(13, "9788580570638", "A Menina que Roubava Livros", "Markus Zusak", "Drama", "Disponivel", "1 Semana"));
        livros.add(new Livro(14, "9788580572243", "O Diário de Anne Frank", "Anne Frank", "Biografia", "Disponivel", "1 Semana"));
        livros.add(new Livro(15, "9788578275782", "O Hobbit", "J.R.R. Tolkien", "Fantasia", "Disponivel", "1 Semana"));
        livros.add(new Livro(16, "9788572329949", "Orgulho e Preconceito", "Jane Austen", "Romance", "Disponivel", "1 Semana"));
        livros.add(new Livro(17, "9788535925609", "A Metamorfose", "Franz Kafka", "Ficção", "Alugado", "1 Semana"));
        livros.add(new Livro(18, "9788525430827", "Crime e Castigo", "Fiódor Dostoiévski", "Romance", "Disponivel", "1 Semana"));
        livros.add(new Livro(19, "9788534908999", "O Retrato de Dorian Gray", "Oscar Wilde", "Clássico", "Disponivel", "1 Semana"));
        livros.add(new Livro(20, "9788573265649", "Anna Karenina", "Liev Tolstói", "Romance", "Disponivel", "1 Semana"));

        model = new CustomTableModel(livros);
        table = new JTable(model);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                
                return cellComponent;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(5).setCellRenderer(statusRenderer);
        table.setFont(new Font("Arial", Font.BOLD, 14));
        table.setRowHeight(40);
        table.setRowSelectionAllowed(false);

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(240, 240, 240));
        header.setForeground(new Color(0, 0, 139));
        header.setFont(new Font("Arial", Font.BOLD, 15));

        addCustomButtonsToTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        TableColumn idColumn = table.getColumnModel().getColumn(0);
        idColumn.setMaxWidth(50);
        idColumn.setMinWidth(50);
        
        TableColumn isbnColumn = table.getColumnModel().getColumn(1);
        isbnColumn.setMaxWidth(200);
        isbnColumn.setMinWidth(200);

        TableColumn authorColumn = table.getColumnModel().getColumn(3);
        authorColumn.setMaxWidth(200);
        authorColumn.setMinWidth(200);

        TableColumn categoryColumn = table.getColumnModel().getColumn(4);
        categoryColumn.setMaxWidth(200);
        categoryColumn.setMinWidth(200);

        TableColumn dispoColumn = table.getColumnModel().getColumn(5);
        dispoColumn.setMaxWidth(200);
        dispoColumn.setMinWidth(200);

        TableColumn actionsColumn = table.getColumnModel().getColumn(6);
        actionsColumn.setMaxWidth(220);
        actionsColumn.setMinWidth(220);
    }

    public GridBagConstraints getConstraints() {
        GridBagConstraints tableConstraints = new GridBagConstraints();
        tableConstraints.gridx = 0;
        tableConstraints.gridy = 3;
        tableConstraints.gridwidth = 2;
        tableConstraints.weightx = 1.0;
        tableConstraints.weighty = 1.0;
        tableConstraints.fill = GridBagConstraints.BOTH;
        tableConstraints.insets = new Insets(20, 20, 67, 30);
        return tableConstraints;
    }

    public void editBook(String isbn, String originalTitle, String newTitle, String author, String category, String status, String rentTime) {
        for (Livro livro : livros) {
            if (livro.getTitulo().equals(originalTitle)) {
                livro.setTitulo(newTitle);
                livro.setAutor(author);
                livro.setCategoria(category);
                livro.setStatus(status);
                livro.setIsbn(isbn);
                livro.setRentTime(rentTime);
                break;
            }
        }
        model.fireTableDataChanged();
    }

    public void editStatus(int row, String status) {
        Livro livro = livros.get(row);
        livro.setStatus(status);
        model.fireTableRowsUpdated(row, row);
    }

    public void searchInTable(String searchTerm) {
        if (searchTerm.equals("")) {
            model.setLivros(livros);
        } else {
            List<Livro> searchResults = new ArrayList<>();
            String lowerCaseSearchTerm = searchTerm.toLowerCase();
            for (Livro livro : livros) {
                if (livro.getTitulo().toLowerCase().contains(lowerCaseSearchTerm) ||
                    livro.getAutor().toLowerCase().contains(lowerCaseSearchTerm) ||
                    livro.getCategoria().toLowerCase().contains(lowerCaseSearchTerm) ||
                    livro.getIsbn().toLowerCase().contains(lowerCaseSearchTerm)) {
                    searchResults.add(livro);
                }
            }
            model.setLivros(searchResults);
        }
        model.fireTableDataChanged();
    }
    

    public void addBook(String isbn, String title, String author, String category, String status, String rentTime) {
        int nextId = getNextID();
        Livro newLivro = new Livro(nextId, title, isbn, author, category, status, rentTime);
        livros.add(newLivro);
    
        Vector<Object> rowData = new Vector<>();
        rowData.add(newLivro.getId());
        rowData.add(newLivro.getIsbn());
        rowData.add(newLivro.getTitulo());
        rowData.add(newLivro.getAutor());
        rowData.add(newLivro.getCategoria());
        rowData.add(newLivro.getStatus());
        rowData.add(""); 
    
        model.addRow(rowData);
    }

    private int getNextID() {
        int maxID = 0;
        for (Livro livro : livros) {
            if (livro.getId() > maxID) {
                maxID = livro.getId();
            }
        }
        return maxID + 1;
    }
    
    private ImageIcon resizeIcon(ImageIcon icon) {
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
        updateTableButtons();
    }
    
    private void addCustomButtonsToTable() {
        if (table.getColumnCount() > 0) {
            TableColumn column = table.getColumnModel().getColumn(table.getColumnCount() - 1);
            column.setCellRenderer(new ButtonRenderer());
            column.setCellEditor(new ButtonEditor());
        }
    }
    
    private void updateTableButtons() {
        addCustomButtonsToTable();
        table.repaint();
    }
    
    private class ButtonRenderer extends DefaultTableCellRenderer {
        private JPanel panel;
        private JButton editButton;
        private JButton deleteButton;
        private JButton rentButton;
    
        public ButtonRenderer() {
            panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));
            panel.setBackground(Color.WHITE);
    
            rentButton = new JButton(resizeIcon(new ImageIcon(getClass().getResource("/icons/rent.png"))));
            editButton = new JButton(resizeIcon(new ImageIcon(getClass().getResource("/icons/edit.png"))));
            deleteButton = new JButton(resizeIcon(new ImageIcon(getClass().getResource("/icons/delete.png"))));
    
            rentButton.setToolTipText("Alugar");
            editButton.setToolTipText("Editar");
            deleteButton.setToolTipText("Excluir");
    
            rentButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    rentButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
            });
    
            editButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    editButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
            });
    
    
            deleteButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
            });
    
            panel.add(rentButton);
            if (admin) {
                panel.add(editButton);
                panel.add(deleteButton);
            }
        }
    
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            panel.setBackground(Color.WHITE);
            return panel;
        }
    }
    
    private class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;
        private JButton editButton;
        private JButton deleteButton;
        private JButton rentButton;
        private Object currentValue;
        private int row;
    
        public ButtonEditor() {
            panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));
            panel.setBackground(Color.WHITE);
    
            rentButton = new JButton(resizeIcon(new ImageIcon(getClass().getResource("/icons/rent.png"))));
            editButton = new JButton(resizeIcon(new ImageIcon(getClass().getResource("/icons/edit.png"))));
            deleteButton = new JButton(resizeIcon(new ImageIcon(getClass().getResource("/icons/delete.png"))));
    
            rentButton.setToolTipText("Alugar");
            editButton.setToolTipText("Editar");
            deleteButton.setToolTipText("Excluir");

            rentButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = table.convertRowIndexToModel(row);
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(CustomTablePanel.this);
                    Livro livro = livros.get(selectedRow);

                    livro.setStatus("Alugado");
            
                    RentBookDialog rentBookDialog = new RentBookDialog(frame, CustomTablePanel.this, livro.getTitulo());
                    rentBookDialog.setTitleField(livro.getTitulo());
                    rentBookDialog.setIsbnField(livro.getIsbn());
                    rentBookDialog.setAuthorField(livro.getAutor());
                    rentBookDialog.setRentTimeField(livro.getRentTime());
                    rentBookDialog.setCategoryComboBox(livro.getCategoria());

                    rentButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    rentButton.setFocusable(false);
                    rentBookDialog.setVisible(true);

                    model.fireTableRowsUpdated(selectedRow, selectedRow);
                }
            });
    
            editButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = table.convertRowIndexToModel(row);
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(CustomTablePanel.this);
                    Livro livro = livros.get(selectedRow);
    
                    AddBookDialog addBookDialog = new AddBookDialog(frame, CustomTablePanel.this, livro.getTitulo());
                    addBookDialog.setTitleField(livro.getTitulo());
                    addBookDialog.setIsbnField(livro.getIsbn());
                    addBookDialog.setAuthorField(livro.getAutor());
                    addBookDialog.setCategoryComboBox(livro.getCategoria());
                    addBookDialog.setRentTime(livro.getRentTime());
                    addBookDialog.setStatusComboBox(livro.getStatus());
    
                    editButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    editButton.setFocusable(false);
                    addBookDialog.setVisible(true);
                }
            });
    
            deleteButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    CustomDeleteConfirmationDialog confirmationDialog = new CustomDeleteConfirmationDialog(null,
                            (String) model.getValueAt(row, 2));
                    confirmationDialog.setVisible(true);
                    deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    deleteButton.setFocusable(false);
            
                    if (confirmationDialog.isConfirmed()) {
                        int selectedRow = table.convertRowIndexToModel(row);
                        model.removeRow(selectedRow);
                        Livro livroRemovido = livros.remove(selectedRow);
                        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(deleteButton);
            
                        SucessMessageDialog.showMessageDialog(parentFrame,
                                "Livro \"" + livroRemovido.getTitulo() + "\" deletado com sucesso!",
                                "Sucesso",
                                new Color(207, 14, 14),
                                Color.WHITE,
                                Color.BLACK,
                                15);
                    }
                }
            });
    
            panel.add(rentButton);
            if (admin) {
                panel.add(editButton);
                panel.add(deleteButton);
            }
        }
    
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            currentValue = value;
            this.row = row;
            panel.setBackground(Color.WHITE);
    
            if (column == table.getColumnCount() - 1) {
                return panel;
            } else {
                return new JLabel();
            }
        }
    
        public Object getCellEditorValue() {
            return currentValue;
        }
    }

    private class StatusRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setHorizontalAlignment(SwingConstants.CENTER);
            String status = (String) value;
            if (status.equals("Disponivel")) {
                cellComponent.setForeground(new Color(34, 139, 34));
            } else if (status.equals("Alugado")) {
                cellComponent.setForeground(new Color(207, 14, 14));
            }
            return cellComponent;
        }
    }
}