package Components.Enum;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "livros")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String isbn;
    private String titulo;
    private String autor;
    private String categoria;
    private String status;
    private String rentTime;
    private String usuario_aluguel;
    private String nome_usuario;
    private String cpf_usuario;
    private String telefone_usuario;
    private String rent_time_user;

    public Book(int id, String isbn, String titulo, String autor, String categoria, String status, String rentTime,
            String usuario_aluguel, String nome_usuario, String cpf_usuario, String telefone_usuario, String rent_time_user) {
        this.id = id;
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.status = status;
        this.rentTime = rentTime;
        this.usuario_aluguel = usuario_aluguel;
        this.nome_usuario = nome_usuario;
        this.cpf_usuario = cpf_usuario;
        this.telefone_usuario = telefone_usuario;
        this.rent_time_user = rent_time_user;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRentTime() {
        return rentTime;
    }

    public void setRentTime(String rentTime) {
        this.rentTime = rentTime;
    }

    public String getUsuarioAluguel() {
        return usuario_aluguel;
    }

    public void setUsuarioAluguel(String usuario_aluguel) {
        this.usuario_aluguel = usuario_aluguel;
    }

    public String getNomeUsuario() {
        return nome_usuario;
    }

    public void setNomeUsuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public String getCpfUsuario() {
        return cpf_usuario;
    }

    public void setCpfUsuario(String cpf_usuario) {
        this.cpf_usuario = cpf_usuario;
    }

    public String getTelefoneUsuario() {
        return telefone_usuario;
    }

    public void setTelefoneUsuario(String telefone_usuario) {
        this.telefone_usuario = telefone_usuario;
    }

    public String getRentTimeUser() {
        return rent_time_user;
    }

    public void setRentTimeUser(String rent_time_user) {
        this.rent_time_user = rent_time_user;
    }
}