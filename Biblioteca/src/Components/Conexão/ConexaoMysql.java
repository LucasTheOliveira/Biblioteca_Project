package Components.Conexão;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Components.Enum.Livro;

public class ConexaoMysql {
    public static String URL = "jdbc:mysql://localhost:3306/biblioteca";
    public static String USER = "root";
    public static String PWD = "##Lucas0407";

    public Connection dbconn = null;
    private Statement sqlmgr = null;
    private ResultSet resultsql = null;

    public Connection getDbConnection() {
        return dbconn;
    }

    public void OpenDataBase() {
        try {
            dbconn = DriverManager.getConnection(URL, USER, PWD);
            System.out.println("Conectado com sucesso em: " + URL);
            sqlmgr = dbconn.createStatement();
        } catch (Exception Error) {
            System.out.println("Error on connect: " + Error.getMessage());
        }
    }

    public void CloseDatabase() throws SQLException {
        if (resultsql != null) {
            resultsql.close();
        }
        if (sqlmgr != null) {
            sqlmgr.close();
        }
        if (dbconn != null) {
            dbconn.close();
        }
    }

    public ResultSet ExecutaQuery(String sql) {
        try {
            return sqlmgr.executeQuery(sql);
        } catch (Exception Error) {
            System.out.println("Error on connect: " + Error.getMessage());
            return null;
        }
    }

    public List<Livro> getLivros() {
        List<Livro> livros = new ArrayList<>();
        try {
            String sql = "SELECT * FROM livros";
            ResultSet resultSet = sqlmgr.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String isbn = resultSet.getString("isbn");
                String titulo = resultSet.getString("titulo");
                String autor = resultSet.getString("autor");
                String categoria = resultSet.getString("categoria");
                String status = resultSet.getString("status");
                String rentTime = resultSet.getString("rentTime");
                String usuario_aluguel = resultSet.getString("usuario_aluguel");
                Livro livro = new Livro(id, isbn, titulo, autor, categoria, status, rentTime, usuario_aluguel);
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livros;
    }

    public String[] getBookOptions() {
        List<String> options = new ArrayList<>();
        try {
            String sql = "SELECT options FROM bookOptions";
            ResultSet resultSet = sqlmgr.executeQuery(sql);
            while (resultSet.next()) {
                String option = resultSet.getString("options");
                options.add(option);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] optionsArray = new String[options.size()];
        optionsArray = options.toArray(optionsArray);
        return optionsArray;
    }

    public void inserirLivro(Livro livro) {
        try {
            String sql = "INSERT INTO livros (isbn, titulo, autor, categoria, status, rentTime) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = dbconn.prepareStatement(sql);
            pstmt.setString(1, livro.getIsbn());
            pstmt.setString(2, livro.getTitulo());
            pstmt.setString(3, livro.getAutor());
            pstmt.setString(4, livro.getCategoria());
            pstmt.setString(5, livro.getStatus());
            pstmt.setString(6, livro.getRentTime());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarLivro(Livro livro) {
        try {
            String sql = "UPDATE livros SET isbn=?, titulo=?, autor=?, categoria=?, status=?, rentTime=?, usuario_aluguel=? WHERE id=?";
            PreparedStatement pstmt = dbconn.prepareStatement(sql);
            pstmt.setString(1, livro.getIsbn());
            pstmt.setString(2, livro.getTitulo());
            pstmt.setString(3, livro.getAutor());
            pstmt.setString(4, livro.getCategoria());
            pstmt.setString(5, livro.getStatus());
            pstmt.setString(6, livro.getRentTime());
            pstmt.setString(7, livro.getUsuarioAluguel());
            pstmt.setInt(8, livro.getId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarLivro(Livro livro) {
        try {
            String sql = "DELETE FROM livros WHERE id=?";
            PreparedStatement pstmt = dbconn.prepareStatement(sql);
            pstmt.setInt(1, livro.getId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarStatusLivro(int idLivro, String novoStatus) {
        try {
            String sql = "UPDATE livros SET status=? WHERE id=?";
            PreparedStatement pstmt = dbconn.prepareStatement(sql);
            pstmt.setString(1, novoStatus);
            pstmt.setInt(2, idLivro);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getRentedBooks(String username) {
        List<String> rentedBooks = new ArrayList<>();
        try {
            String sql = "SELECT rentedBooks FROM usuarios WHERE nome=?";
            PreparedStatement pstmt = dbconn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                String rentedBooksStr = resultSet.getString("rentedBooks");
                if (rentedBooksStr != null) {
                    String[] rentedBooksArray = rentedBooksStr.split(",");
                    rentedBooks.addAll(Arrays.asList(rentedBooksArray));
                }
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentedBooks;
    }

    public void updateRentedBooks(String username, List<String> rentedBooks) {
        try {
            String rentedBooksStr = String.join(",", rentedBooks);
            String sql = "UPDATE usuarios SET rentedBooks=? WHERE nome=?";
            PreparedStatement pstmt = dbconn.prepareStatement(sql);
            pstmt.setString(1, rentedBooksStr);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserirUsuario(String nome, String senha) {
        try {
            String sql = "INSERT INTO usuarios (nome, senha) VALUES (?, ?)";
            PreparedStatement pstmt = dbconn.prepareStatement(sql);
            pstmt.setString(1, nome);
            pstmt.setString(2, senha);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}