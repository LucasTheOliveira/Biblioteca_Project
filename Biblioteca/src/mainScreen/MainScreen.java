package mainScreen;

import javax.swing.*;
import java.awt.*;

import Components.SearchPanel.*;
import Components.customButton.*;
import Components.customTable.*;
import Components.customTitle.*;

public class MainScreen extends JFrame {
    public MainScreen(boolean admin) {
        setTitle("Biblioteca UNAERP");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        getContentPane().add(mainPanel);

        // Label title "Biblioteca unaerp"
        TitlePanel titlePanel = new TitlePanel();
        mainPanel.add(titlePanel, titlePanel.getConstraints());

        // Label "Lista de Livros"
        BookListLabel bookListLabel = new BookListLabel("");
        bookListLabel.setText("Lista de Livros");
        GridBagConstraints bookListconstraints = bookListLabel.getConstraints(0, 1, GridBagConstraints.WEST,
                new Insets(10, 20, 0, 0));
        mainPanel.add(bookListLabel, bookListconstraints);

        // Campo de Pesquisa
        SearchField searchField = new SearchField();
        mainPanel.add(searchField, searchField.getConstraints());

        // Tabela de Livros
        CustomTablePanel tablePanel = new CustomTablePanel();
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
        CustomButton addButton = new CustomButton(tablePanel);
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (admin) {
            mainPanel.add(addButton, addButton.getConstraints());
        }
    }
}