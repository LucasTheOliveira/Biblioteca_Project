package Components.userTable;

import javax.swing.*;
import javax.swing.table.*;

import Components.Enum.User;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import Components.CustomDialogs.AddUserDialog;
import Components.CustomDialogs.CustomDeleteConfirmationDialog;
import Components.CustomDialogs.SuccessMessageDialog;
import Components.Enum.UserType;
import Conection.ConectionSql;

// CLASSE DO PAINEL DA TABELA
public class UserTable extends JPanel {
    private JTable table;
    private UserTableModel model;
    private List<User> usuarios;
    @SuppressWarnings("unused")
    private boolean admin;

    public UserTable(boolean admin, List<User> usuarios) {
        this.admin = admin;
        this.usuarios = usuarios != null ? usuarios : new ArrayList<>();

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 400));

        model = new UserTableModel(usuarios);
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

        TableColumn nomeColumn = table.getColumnModel().getColumn(1);
        nomeColumn.setMaxWidth(600);
        nomeColumn.setMinWidth(600);

        TableColumn rentedBooksColumn = table.getColumnModel().getColumn(2);
        rentedBooksColumn.setMaxWidth(650);
        rentedBooksColumn.setMinWidth(650);

        TableColumn tipColumn = table.getColumnModel().getColumn(3);
        tipColumn.setMaxWidth(350);
        tipColumn.setMinWidth(350);

        TableColumn dispoColumn = table.getColumnModel().getColumn(4);
        dispoColumn.setMaxWidth(220);
        dispoColumn.setMinWidth(220);
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

    public void searchInUserTable(String searchTerm) {
        if (searchTerm.equals("")) {
            model.setUsers(usuarios);
        } else {
            List<User> searchResults = new ArrayList<>();
            String lowerCaseSearchTerm = searchTerm.toLowerCase();
            for (User usuario : usuarios) {
                if (usuario.getNome().toLowerCase().contains(lowerCaseSearchTerm)) {
                    searchResults.add(usuario);
                }
            }
            model.setUsers(searchResults);
        }
        model.fireTableDataChanged();
    }

    public void editUser(String originalName, String newName, String senha, UserType tipo, List<String> rentedBooks) throws SQLException {
        for (User usuario : usuarios) {
            if (usuario.getNome().equals(originalName)) {
                usuario.setNome(newName);
                usuario.setSenha(senha);
                usuario.setTipo(tipo);
                usuario.setRentedBooks(rentedBooks);

                ConectionSql conexao = new ConectionSql();
                conexao.OpenDataBase();
                conexao.atualizarUsuario(usuario);
                conexao.CloseDatabase();
                break;
            }
        }
        model.fireTableDataChanged();
    }

    public void addUser(String name, String senha, UserType tipo, List<String> rentedBooks) throws SQLException {
        int nextId = getNextID();
        User newUser = new User(nextId, name, senha, tipo, rentedBooks);
        usuarios.add(newUser);

        ConectionSql conexao = new ConectionSql();
        conexao.OpenDataBase();
        conexao.inserirUsuario(newUser);
        conexao.CloseDatabase();

        Vector<Object> rowData = new Vector<>();
        rowData.add(newUser.getId());
        rowData.add(newUser.getNome());
        rowData.add(newUser.getRentedBooks());
        rowData.add(newUser.getTipo());
        rowData.add("");

        model.addRow(rowData);
        searchInUserTable("");
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

        public ButtonRenderer() {
            panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));
            panel.setBackground(Color.WHITE);

            editButton = new JButton(resizeIcon(new ImageIcon(getClass().getResource("/icons/edit.png"))));
            deleteButton = new JButton(resizeIcon(new ImageIcon(getClass().getResource("/icons/delete.png"))));

            editButton.setToolTipText("Editar");
            deleteButton.setToolTipText("Excluir");

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

            panel.add(editButton);
            panel.add(deleteButton);
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
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(UserTable.this);
                    User usuario = usuarios.get(selectedRow);

                    AddUserDialog addUserDialog = new AddUserDialog(frame, UserTable.this, usuario.getNome());
                    addUserDialog.setUsername(usuario.getNome());
                    addUserDialog.setPassword(usuario.getSenha());
                    addUserDialog.selectUserType(usuario.getTipo());

                    editButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    editButton.setFocusable(false);
                    addUserDialog.setVisible(true);
                }
            });

            deleteButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String usuarioNome = (String) model.getValueAt(row, 1);

                    CustomDeleteConfirmationDialog confirmationDialog = new CustomDeleteConfirmationDialog(null,
                            "Tem certeza de que deseja deletar o usuario \"" + usuarioNome + "\"?",
                            new ImageIcon(getClass().getResource("/icons/delete.png")));
                    confirmationDialog.setVisible(true);

                    if (confirmationDialog.isConfirmed()) {
                        if (table.isEditing()) {
                            table.getCellEditor().cancelCellEditing();
                        }
                        
                        int selectedRow = table.convertRowIndexToModel(row);
                        User usuarioRemovido = usuarios.get(selectedRow);

                        try {
                            ConectionSql conexao = new ConectionSql();
                            conexao.OpenDataBase();
                            conexao.deletarUsuario(usuarioRemovido);
                            conexao.CloseDatabase();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                        model.removeRow(selectedRow);
                        usuarios.remove(selectedRow);
                        searchInUserTable("");
                        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(deleteButton);

                        SuccessMessageDialog.showMessageDialog(parentFrame,
                                "Livro \"" + usuarioRemovido.getNome() + "\" deletado com sucesso!",
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
}