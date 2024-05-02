package mainScreen;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Components.SearchPanel.*;
import Components.customButton.*;
import Components.customTable.*;
import Components.customTitle.*;
import Components.Conexão.ConexaoMysql;
import Components.Enum.Livro;

public class MainScreen extends JFrame {
    private CustomTablePanel tablePanel;
    private CustomButton addButton;
    private BookListLabel bookListLabel;
    private boolean userTableOn;
    private boolean bookTableOn;

    public MainScreen(boolean admin) {
        setTitle("Biblioteca UNAERP");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.WHITE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        ConexaoMysql conexao = new ConexaoMysql();

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        getContentPane().add(mainPanel);

        // Label title "Biblioteca unaerp"
        TitlePanel titlePanel = new TitlePanel(this, admin, this);
        mainPanel.add(titlePanel, titlePanel.getConstraints());

        // Label "Lista de Livros"
        bookListLabel = new BookListLabel("");
        bookListLabel.setText("Lista de Livros");
        GridBagConstraints bookListconstraints = bookListLabel.getConstraints(0, 1, GridBagConstraints.WEST,
                new Insets(10, 20, 0, 0));
        mainPanel.add(bookListLabel, bookListconstraints);

        // Campo de Pesquisa
        SearchField searchField = new SearchField();
        mainPanel.add(searchField, searchField.getConstraints());

        // Tabela de Livros
        conexao.OpenDataBase();
        List<Livro> livros = conexao.getLivros();
        tablePanel = new CustomTablePanel(admin, livros);
        tablePanel.setAdmin(admin);
        mainPanel.add(tablePanel, tablePanel.getConstraints());

        // Botão "Limpar filtro"
        ClearFilterButton clearFilterButton = new ClearFilterButton(searchField, tablePanel);
        clearFilterButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainPanel.add(clearFilterButton, clearFilterButton.getConstraints());

        // Botão "Pesquisa"
        SearchButton searchButton = new SearchButton(searchField, tablePanel);
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainPanel.add(searchButton, searchButton.getConstraints());

        // Botão "Adicionar Livro"
        addButton = new CustomButton(tablePanel);
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (admin) {
            mainPanel.add(addButton, addButton.getConstraints());
        }

        conexao.OpenDataBase();
        String sql = "SELECT * FROM usuarios";
        ResultSet resultSet = conexao.ExecutaQuery(sql);
        int rowCount = 0;
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    rowCount++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Número de linhas afetadas: " + rowCount);

        try {
            conexao.CloseDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showUserTable() {
        tablePanel.setVisible(false);
        addButton.setVisible(false);
        bookListLabel.setText("Lista de Usuários");
        userTableOn = true;
    }

    public void showBookTable() {
        tablePanel.setVisible(true);
        addButton.setVisible(true);
        bookListLabel.setText("Lista de Livros");
        userTableOn = false;
    }

    public boolean isUserTableOn() {
        return userTableOn;
    }

    public void setUserTableOn(boolean userTableOn) {
        this.userTableOn = userTableOn;
    }

    public boolean isBookTableOn() {
        return bookTableOn;
    }

    public void setBookTableOn(boolean bookTableOn) {
        this.bookTableOn = bookTableOn;
    }
}