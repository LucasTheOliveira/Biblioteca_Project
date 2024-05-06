package Components.userTable;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import Components.Enum.Usuario;

public class UserTableModel extends DefaultTableModel {
    private final static String[] columnNames = { "ID", "Nome", "Livros Alugados", "Tipo", "Ações" };
    private List<Usuario> usuarios;

    public UserTableModel(List<Usuario> usuarios) {
        super(new Object[usuarios.size()][columnNames.length + 1], columnNames);
        this.usuarios = usuarios;
        updateTableModel();
    }

    public void setUsers(List<Usuario> usuarios) {
        this.usuarios = new ArrayList<>(usuarios);
        updateTableModel();
    }

    private void updateTableModel() {
        setRowCount(0);
        for (Usuario usuario : usuarios) {
            Object[] rowData = { usuario.getId(), usuario.getNome(), usuario.getRentedBooks(), usuario.getTipo(), "" };
            addRow(rowData);
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == getColumnCount() - 1;
    }

    @Override
    public Object getValueAt(int row, int column) {
        Usuario usuario = usuarios.get(row);
        switch (column) {
            case 0:
                return usuario.getId();
            case 1:
                return usuario.getNome();
            case 2:
                return usuario.getRentedBooks();
            case 3:
                return usuario.getTipo();
            default:
                return null;
        }
    }
}