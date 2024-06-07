package Components.Enum;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import Conection.HibernateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookDAO {
    private static SessionFactory factory;

    static {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() {
        return factory.openSession();
    }

    public List<Book> getLivros() {
        Session session = getSession();
        Transaction tx = null;
        List<Book> livros = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            livros = session.createQuery("FROM Book", Book.class).list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return livros;
    }

    public void inserirLivro(Book livro) {
        Session session = getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(livro);
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void atualizarLivro(Book livro) {
        Session session = getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(livro);
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void deletarLivro(Book livro) {
        Session session = getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(livro);
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void atualizarStatusLivro(int idLivro, String novoStatus) {
        Session session = getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Book livro = session.get(Book.class, idLivro);
            livro.setStatus(novoStatus);
            session.update(livro);
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}