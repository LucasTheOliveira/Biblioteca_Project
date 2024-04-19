package Components.Enum;

import java.util.List;

public class Usuario {
    private int id;
    private String nome;
    private String senha;
    private UserType tipo;
    private List<String> rentedBooks;

    public Usuario(int id, String nome, String senha, UserType tipo, List<String> rentedBooks) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.tipo = tipo;
        this.rentedBooks = rentedBooks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getRentedBooks() {
        return rentedBooks;
    }

    public void setRentedBooks(List<String> rentedBooks) {
        this.rentedBooks = rentedBooks;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public UserType getTipo() {
        return tipo;
    }

    public void setTipo(UserType tipo) {
        this.tipo = tipo;
    }
}