package Components.BookTable;

import javax.swing.*;
import javax.swing.table.*;

import Components.CustomDialogs.AddBookDialog;
import Components.CustomDialogs.CustomDeleteConfirmationDialog;
import Components.CustomDialogs.RentBookDialog;
import Components.CustomDialogs.SuccessMessageDialog;
import Components.Enum.Book;
import Conection.ConectionSql;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

// CLASSE DO PAINEL DA TABELA
public class BookTablePanel extends JPanel {
    private JTable table;
    private BookTableModel model;
    private List<Book> livros;
    private boolean admin;

    public BookTablePanel(boolean admin, List<Book> livros) {
        this.admin = admin;
        this.livros = livros != null ? livros : new ArrayList<>();

        TableCellRenderer statusRenderer = new StatusRenderer();

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 400));

        model = new BookTableModel(livros);
        table = new JTable(model);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                        column);
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

    public void editBook(String originalTitle, String newTitle, String isbn, String author, String category,
            String status, String rentTime) throws SQLException {
        for (Book livro : livros) {
            if (livro.getTitulo().equals(originalTitle)) {
                livro.setTitulo(newTitle);
                livro.setAutor(author);
                livro.setCategoria(category);
                livro.setStatus(status);
                livro.setIsbn(isbn);
                livro.setRentTime(rentTime);

                ConectionSql conexao = new ConectionSql();
                conexao.OpenDataBase();
                conexao.atualizarLivro(livro);
                conexao.CloseDatabase();
                break;
            }
        }
        model.fireTableDataChanged();
    }

    public void editStatus(int row, String status) {
        Book livro = livros.get(row);
        livro.setStatus(status);
        model.fireTableRowsUpdated(row, row);
    }

    public void searchInTable(String searchTerm) {
        if (searchTerm.equals("")) {
            model.setLivros(livros);
        } else {
            List<Book> searchResults = new ArrayList<>();
            String lowerCaseSearchTerm = searchTerm.toLowerCase();
            for (Book livro : livros) {
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

    public void addBook(String isbn, String title, String author, String category, String status, String rentTime,
            String usuario_aluguel, String nome_usuario, String cpf_usuario, String telefone_usuario, String rent_time_user) throws SQLException {
        int nextId = getNextID();
        Book newLivro = new Book(nextId, title, isbn, author, category, status, rentTime, usuario_aluguel, nome_usuario, cpf_usuario, telefone_usuario, rent_time_user);
        livros.add(newLivro);

        // Adicionar o novo livro ao banco de dados
        ConectionSql conexao = new ConectionSql();
        conexao.OpenDataBase();
        conexao.inserirLivro(newLivro);
        conexao.CloseDatabase();

        Vector<Object> rowData = new Vector<>();
        rowData.add(newLivro.getId());
        rowData.add(newLivro.getIsbn());
        rowData.add(newLivro.getTitulo());
        rowData.add(newLivro.getAutor());
        rowData.add(newLivro.getCategoria());
        rowData.add(newLivro.getStatus());
        rowData.add(newLivro.getRentTime());
        rowData.add("");

        model.addRow(rowData);
        searchInTable("");
    }

    private int getNextID() {
        int maxID = 0;
        for (Book livro : livros) {
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
            panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
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
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(BookTablePanel.this);
                    Book livro = livros.get(selectedRow);

                    RentBookDialog rentBookDialog = new RentBookDialog(frame, BookTablePanel.this, livro.getTitulo(),
                            livro);
                    rentBookDialog.setSelectedRow(selectedRow);
                    rentBookDialog.setTitleField(livro.getTitulo());
                    rentBookDialog.setIsbnField(livro.getIsbn());
                    rentBookDialog.setAuthorField(livro.getAutor());
                    rentBookDialog.setRentTimeField(livro.getRentTime());
                    rentBookDialog.setCategoryComboBox(livro.getCategoria());

                    rentButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    rentButton.setFocusable(false);
                    rentBookDialog.setVisible(true);
                }
            });

            editButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = table.convertRowIndexToModel(row);
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(BookTablePanel.this);
                    Book livro = livros.get(selectedRow);

                    AddBookDialog addBookDialog = new AddBookDialog(frame, BookTablePanel.this, livro.getTitulo());
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
                    String bookTitle = (String) model.getValueAt(row, 2);
            
                    CustomDeleteConfirmationDialog confirmationDialog = new CustomDeleteConfirmationDialog(null,
                            "Tem certeza de que deseja deletar o livro \"" + bookTitle + "\"?",
                            new ImageIcon(getClass().getResource("/icons/delete.png")));
                    confirmationDialog.setVisible(true);
            
                    if (confirmationDialog.isConfirmed()) {
                        if (table.isEditing()) {
                            table.getCellEditor().cancelCellEditing();
                        }
            
                        int selectedRow = table.convertRowIndexToModel(row);
                        Book livroRemovido = livros.get(selectedRow);
            
                        try {
                            ConectionSql conexao = new ConectionSql();
                            conexao.OpenDataBase();
                            conexao.deletarLivro(livroRemovido);
                            conexao.CloseDatabase();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
            
                        model.removeRow(selectedRow);
                        livros.remove(selectedRow);
                        searchInTable("");
                        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(deleteButton);
            
                        SuccessMessageDialog.showMessageDialog(parentFrame,
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
            panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

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
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                    column);
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