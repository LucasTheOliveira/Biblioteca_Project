package loginScreen;

import java.util.ArrayList;
import java.util.List;

public class CurrentUser {
    private static CurrentUser instance;
    private String username;
    private String userId;
    private List<String> rentedBooks;
    private boolean isAdmin;

    private CurrentUser() {
        rentedBooks = new ArrayList<>();
    }

    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    public String getUserId() {
        return userId;
    }

    public List<String> getRentedBooks() {
        return rentedBooks;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    // Método para adicionar um livro ao rentedBooks
    public void addRentedBook(String bookTitle) {
        rentedBooks.add(bookTitle);
    }

    public void atualizarRentedBooks() {

    }
}