package Components.Enum;

public class Book {
    private int id;
    private String isbn;
    private String titulo;
    private String autor;
    private String categoria;
    private String status;
    private String rentTime;
    private String usuario_aluguel;

    public Book(int id, String isbn, String titulo, String autor, String categoria, String status, String rentTime,
            String usuario_aluguel) {
        this.id = id;
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.status = status;
        this.rentTime = rentTime;
        this.usuario_aluguel = usuario_aluguel;
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
}