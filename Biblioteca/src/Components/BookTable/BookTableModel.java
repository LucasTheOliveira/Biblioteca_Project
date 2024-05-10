package Components.BookTable;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import Components.Enum.Book;

public class BookTableModel extends DefaultTableModel {
    private final static String[] columnNames = { "ID", "ISBN", "Título", "Autor", "Categoria", "Status", "Ações" };
    private List<Book> livros;

    public BookTableModel(List<Book> livros) {
        super(new Object[livros.size()][columnNames.length], columnNames);
        this.livros = livros;
        updateTableModel();
    }

    public void setLivros(List<Book> livros) {
        this.livros = new ArrayList<>(livros);
        updateTableModel();
    }

    private void updateTableModel() {
        setRowCount(0);
        for (Book livro : livros) {
            addRow(new Object[] { livro.getId(), livro.getIsbn(), livro.getTitulo(), livro.getAutor(),
                    livro.getCategoria(), livro.getStatus(), null });
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == getColumnCount() - 1;
    }

    @Override
    public Object getValueAt(int row, int column) {
        Book livro = livros.get(row);
        switch (column) {
            case 0:
                return livro.getId();
            case 1:
                return livro.getIsbn();
            case 2:
                return livro.getTitulo();
            case 3:
                return livro.getAutor();
            case 4:
                return livro.getCategoria();
            case 5:
                return livro.getStatus();
            default:
                return null;
        }
    }
}