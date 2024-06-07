package Components.Enum;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "usuarios")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "senha")
    private String senha;

    @Convert(converter = UserTypeConverter.class)
    @Column(name = "tipo")
    private UserType tipo;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "rentedBooks")
    private List<String> rentedBooks;

    public User(int id, String nome, String senha, UserType tipo, List<String> rentedBooks) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.tipo = tipo;
        this.rentedBooks = rentedBooks;
    }

    public User() {
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