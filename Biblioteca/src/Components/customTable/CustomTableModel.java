package Components.customTable;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import Components.Enum.Livro;

// CLASSE DO MODELO DA TABELA
public class CustomTableModel extends DefaultTableModel {
// DEFINIÇÃO DO CABESALHO (HEADER) DA TABELA
    private final static String[] columnNames = { "ID", "Título", "Autor", "Categoria", "Ações" };

    private List<Livro> livros;

    public CustomTableModel(List<Livro> livros) {
        super(new Object[livros.size()][columnNames.length], columnNames);
        this.livros = livros;
    }

// METODO RELACIONADO AO SEARCH, LIMPA TODAS AS LINHAS EXISTENTES NA TABELA E ADICIONA AS LINHAS CORRESPONDER AOS LIVROS DA LISTA ATUALIZADA
    private void updateTableModel() {
        setRowCount(0);
        for (Livro livro : livros) {
            addRow(new Object[] { livro.getId(), livro.getTitulo(), livro.getAutor(), livro.getCategoria() });
        }
    }

// METODO RELACIONADO AO SEARCH, DEFINE UMA NOVA LISTA DE LIVROS E ATUALIZA O MODELO DA TABELA CHAMANDO O METODO UPDATEMODEL
    public void setLivros(List<Livro> livros) {
        this.livros = new ArrayList<>(livros);
        updateTableModel();
    }

// DETERMINA SE UMA COLUNA EXPECIFICA DA TABELA É EDITAVEL
    @Override
    public boolean isCellEditable(int row, int column) {
        return column == getColumnCount() - 1;
    }

// METODO RELACIONADO AO SEARCH, RETORNA O VALOR A SER EXBIDO EM UMA CELULA EXPECIFICA DA TABELA
    @Override
    public Object getValueAt(int row, int column) {
        Livro livro = livros.get(row);
        switch (column) {
            case 0:
                return livro.getId();
            case 1:
                return livro.getTitulo();
            case 2:
                return livro.getAutor();
            case 3:
                return livro.getCategoria();
            default:
                return null;
        }
    }
}