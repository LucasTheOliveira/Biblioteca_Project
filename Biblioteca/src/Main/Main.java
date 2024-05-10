package Main;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Components.SearchPanel.*;
import Components.customTitle.*;
import Components.userTable.UserTable;
import Conection.ConectionSql;
import Components.AddButtons.*;
import Components.BookTable.*;
import Components.Enum.Book;
import Components.Enum.User;

public class Main extends JFrame {
    private BookTablePanel tablePanel;
    private UserTable userTablePanel;
    private AddBookButton addButton;
    private AddUserButton addUserButton;
    private BookListLabel bookListLabel;
    private boolean userTableOn;
    private boolean bookTableOn;

    public Main(boolean admin) {
        setTitle("Biblioteca UNAERP");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.WHITE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        ConectionSql conexao = new ConectionSql();

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
        SearchField searchField = new SearchField(this);
        mainPanel.add(searchField, searchField.getConstraints());

        // Tabela de Livros
        conexao.OpenDataBase();
        List<Book> livros = conexao.getLivros();
        tablePanel = new BookTablePanel(admin, livros);
        tablePanel.setAdmin(admin);
        mainPanel.add(tablePanel, tablePanel.getConstraints());

        // Tabela de Livros
        conexao.OpenDataBase();
        List<User> users = conexao.getUsers();
        userTablePanel = new UserTable(admin, users);
        userTablePanel.setAdmin(admin);
        mainPanel.add(userTablePanel, userTablePanel.getConstraints());

        // Botão "Limpar filtro"
        ClearFilterButton clearFilterButton = new ClearFilterButton(searchField, tablePanel, userTablePanel, this);
        clearFilterButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainPanel.add(clearFilterButton, clearFilterButton.getConstraints());

        // Botão "Pesquisa"
        SearchButton searchButton = new SearchButton(searchField, tablePanel, userTablePanel, this);
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainPanel.add(searchButton, searchButton.getConstraints());

        // Botão "Adicionar Livro"
        addButton = new AddBookButton(tablePanel, this);
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (admin) {
            mainPanel.add(addButton, addButton.getConstraints());
        }

        // Botão "Adicionar Usuario"
        addUserButton = new AddUserButton(userTablePanel, this);
        addUserButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (admin) {
            mainPanel.add(addUserButton, addUserButton.getConstraints());
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
        userTablePanel.setVisible(true);
        addUserButton.setVisible(true);
        addButton.setVisible(false);
        bookListLabel.setText("Lista de Usuários");
        userTableOn = true;
    }

    public void showBookTable() {
        tablePanel.setVisible(true);
        userTablePanel.setVisible(false);
        addUserButton.setVisible(false);
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