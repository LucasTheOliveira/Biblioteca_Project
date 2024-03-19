package Components.Enum;

// CLASSE QUE DEFINE OS DADOS E OS TIPOS USADOS PARA LIVRO
public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private String categoria;

// INICIALIZA O OBJETO LIVRO
    public Livro(int id, String titulo, String autor, String categoria) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
    }

// RETORNA O ID DO LIVRO
    public int getId() {
        return id;
    }

// DEFINE O ID DO LIVRO COM O VALOR FORNECIDO
    public void setId(int id) {
        this.id = id;
    }

// RETORNA O TITULO DO LIVRO
    public String getTitulo() {
        return titulo;
    }

// DEFINE O TITULO DO LIVRO COM O VALOR FORNECIDO
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

// RETORNA O AUTOR DO LIVRO
    public String getAutor() {
        return autor;
    }

// DEFINE O AUTOR DO LIVRO COM O VALOR FORNECIDO
    public void setAutor(String autor) {
        this.autor = autor;
    }

// RETORNA A CATEGORIA DO LIVRO
    public String getCategoria() {
        return categoria;
    }

// DEFINE A CATEGORIA COM O VALOR FORNECIDO
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}