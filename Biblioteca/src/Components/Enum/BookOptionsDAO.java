package Components.Enum;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class BookOptionsDAO {
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

    public String[] getBookOptions() {
        List<String> options = new ArrayList<>();
        try (Session session = getSession()) {
            Transaction tx = session.beginTransaction();
            Query<String> query = session.createQuery("SELECT bo.options FROM BookOptions bo", String.class);
            List<String> bookOptionsList = query.getResultList();
            options.addAll(bookOptionsList);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return options.toArray(new String[0]);
    }
}