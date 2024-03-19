package Components.customTable;

import javax.swing.*;
import javax.swing.table.*;

import Components.Enum.Livro;
import Components.customDialog.AddBookDialog;
import Components.customDialog.CustomDeleteConfirmationDialog;
import Components.customDialog.SucessMessageDialog;

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

    public CustomTablePanel() {
        setLayout(new BorderLayout());
// DEFINE O TAMANHO DA TABELA
        setPreferredSize(new Dimension(800, 400));

// POPULA A TABELA DE LIVROS 
        livros = new ArrayList<>();
        livros.add(new Livro(1, "Livro 1", "Autor 1", "Terror"));
        livros.add(new Livro(2, "Livro 2", "Autor 2", "Suspense"));
        livros.add(new Livro(1, "Livro 1", "Autor 1", "Terror"));
        livros.add(new Livro(2, "Livro 2", "Autor 2", "Suspense"));
        livros.add(new Livro(1, "Livro 1", "Autor 1", "Terror"));
        livros.add(new Livro(2, "Livro 2", "Autor 2", "Suspense"));
        livros.add(new Livro(1, "Livro 1", "Autor 1", "Terror"));
        livros.add(new Livro(2, "Livro 2", "Autor 2", "Suspense"));
        livros.add(new Livro(1, "Livro 1", "Autor 1", "Terror"));
        livros.add(new Livro(2, "Livro 2", "Autor 2", "Suspense"));
        livros.add(new Livro(1, "Livro 1", "Autor 1", "Terror"));
        livros.add(new Livro(2, "Livro 2", "Autor 2", "Suspense"));
        livros.add(new Livro(1, "Livro 1", "Autor 1", "Terror"));
        livros.add(new Livro(2, "Livro 2", "Autor 2", "Suspense"));
        livros.add(new Livro(1, "Livro 1", "Autor 1", "Terror"));
        livros.add(new Livro(2, "Livro 2", "Autor 2", "Suspense"));
        livros.add(new Livro(1, "Livro 1", "Autor 1", "Terror"));
        livros.add(new Livro(2, "Livro 2", "Autor 2", "Suspense"));
        livros.add(new Livro(1, "Livro 1", "Autor 1", "Terror"));
        livros.add(new Livro(2, "Livro 2", "Autor 2", "Suspense"));
        livros.add(new Livro(1, "Livro 1", "Autor 1", "Terror"));
        livros.add(new Livro(2, "Livro 2", "Autor 2", "Suspense"));

// INSTANCIA A TABELA COM LIVROS COMO ARGUMENTO PARA INICIALIZAR A TABELA POPULADA
        model = new CustomTableModel(livros);
        table = new JTable(model);

// DEFINIÇÃO DE ESTILO DO HEADER DA TABELA
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(240, 240, 240));
        header.setForeground(new Color(0, 0, 139));
        header.setFont(new Font("Arial", Font.BOLD, 14));

// ADIÇÃO DOS BOTÕES DE AÇÃO AH TABELA
        addCustomButtonsToTable();
        add(new JScrollPane(table), BorderLayout.CENTER);
        table.setRowHeight(40);
        table.setRowSelectionAllowed(false);
    }

// METODO PARA EDITAR UM LIVRO
    public void editBook(String originalTitle, String newTitle, String author, String category) {
        for (Livro livro : livros) {
            if (livro.getTitulo().equals(originalTitle)) {
                livro.setTitulo(newTitle);
                livro.setAutor(author);
                livro.setCategoria(category);
                break;
            }
        }
        model.fireTableDataChanged();
    }

// METODO DE PESQUISA DOS LIVROS NA TABELA. SE ESTIVER VAZIO MOSTRA TODOS OS LIVROS (LINHA 85 E 86)
    public void searchInTable(String searchTerm) {
        if (searchTerm.equals("")) {
            model.setLivros(livros);
        } else {
            List<Livro> searchResults = new ArrayList<>();
            for (Livro livro : livros) {
                if (livro.getTitulo().toLowerCase().contains(searchTerm.toLowerCase())) {
                    searchResults.add(livro);
                }
            }
            model.setLivros(searchResults);
        }
        model.fireTableDataChanged();
    }

// METODO PARA CRIAR O LIVRO E ADICIONAR A TABELA
    public void addBook(String title, String author, String category) {
        int nextId = getNextID();
        Livro newLivro = new Livro(nextId, title, author, category);
        livros.add(newLivro);

        // Convertendo o Livro em um vetor
        Vector<Object> rowData = new Vector<>();
        rowData.add(newLivro.getId());
        rowData.add(newLivro.getTitulo());
        rowData.add(newLivro.getAutor());
        rowData.add(newLivro.getCategoria());

        // Adicionando a nova linha ao modelo da tabela
        model.addRow(rowData);
    }

// METODO PARA MANDER OS IDS EM ORDEM CRESCENTE, ELE VERIFICA O ID DO ULTIMO ITEM DA LISTA E ACRESCENTA +1
    private int getNextID() {
        int maxID = 0;
        for (Livro livro : livros) {
            if (livro.getId() > maxID) {
                maxID = livro.getId();
            }
        }
        return maxID + 1;
    }

// DEFINIÇÃO DO LAYOUT DA TABELA NA APLICAÇÃO
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

// ADICIONA OS BOTÕES DE AÇÃO DA TABELA
    private void addCustomButtonsToTable() {
        if (table.getColumnCount() > 0) {
            TableColumn column = table.getColumnModel().getColumn(table.getColumnCount() - 1);
            column.setCellRenderer(new ButtonRenderer());
            column.setCellEditor(new ButtonEditor());
        }
    }

// RENDEREIZA OS BOTÕES DE AÇÃO NA TABELA
    private class ButtonRenderer extends DefaultTableCellRenderer {
        private JPanel panel;
        private JButton editButton;
        private JButton deleteButton;

        public ButtonRenderer() {
// DEFINIÇÃO DO PAINEL DOS BOTÕES DE AÇÃO
            panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));
            panel.setBackground(Color.WHITE);

// DEFINIÇÃO DOS BOTÕES COM SEUS RESPECTIVOS ICONIS
            editButton = new JButton(resizeIcon(new ImageIcon(getClass().getResource("/icons/edit.png"))));
            deleteButton = new JButton(resizeIcon(new ImageIcon(getClass().getResource("/icons/delete.png"))));

// DEFINIÇÃO DOS TOOLTIPS DOS BOTÕES ( FUNCIONANDO???? )
            editButton.setToolTipText("Editar");
            deleteButton.setToolTipText("Excluir");

// LISTENER PARA DETECTAR QUANDO O MOUSE PASSA POR CIMA DO BOTÃO DE EDIT E MUDAR O CURSOR
            editButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    editButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
            });

// LISTENER PARA DETECTAR QUANDO O MOUSE PASSA POR CIMA DO BOTÃO DE DELEÇÃO E MUDAR O CURSOR
            deleteButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
            });

// ADICIONA OS BOTÕES AO PANEL DE BOTÕES
            panel.add(editButton);
            panel.add(deleteButton);
        }

// ADICIONA OS BOTÕES AOS CAMPOS DA TABELA
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
        private Object currentValue;
        private int row;

        public ButtonEditor() {
            panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));
            panel.setBackground(Color.WHITE);

            editButton = new JButton(resizeIcon(new ImageIcon(getClass().getResource("/icons/edit.png"))));
            deleteButton = new JButton(resizeIcon(new ImageIcon(getClass().getResource("/icons/delete.png"))));

            editButton.setToolTipText("Editar");
            deleteButton.setToolTipText("Excluir");

            editButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = table.convertRowIndexToModel(row);
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(CustomTablePanel.this);
                    Livro livro = livros.get(selectedRow);

                    AddBookDialog addBookDialog = new AddBookDialog(frame, CustomTablePanel.this, livro.getTitulo());
                    addBookDialog.setTitleField(livro.getTitulo());
                    addBookDialog.setAuthorField(livro.getAutor());
                    addBookDialog.setCategoryComboBox(livro.getCategoria());

                    editButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    editButton.setFocusable(false);
                    addBookDialog.setVisible(true);
                }
            });

            deleteButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    CustomDeleteConfirmationDialog confirmationDialog = new CustomDeleteConfirmationDialog(null,
                            (String) model.getValueAt(row, 1));
                    confirmationDialog.setVisible(true);
                    deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    deleteButton.setFocusable(false);

                    if (confirmationDialog.isConfirmed()) {
                        model.removeRow(row);
                        Livro livroRemovido = livros.remove(row); // Removendo o livro da lista e armazenando em uma
                                                                  // variável
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
        
            panel.add(editButton);
            panel.add(deleteButton);
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            currentValue = value;
            this.row = row;
            panel.setBackground(Color.WHITE);
            return panel;
        }

        public Object getCellEditorValue() {
            return currentValue;
        }
    }

// METODO PARA MANIPULZAÇÃO DO TAMANHO DO ICONI
    private ImageIcon resizeIcon(ImageIcon icon) {
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }
}