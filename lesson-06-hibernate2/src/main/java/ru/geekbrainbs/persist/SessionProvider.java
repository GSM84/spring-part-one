package ru.geekbrainbs.persist;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.function.Consumer;
import java.util.function.Function;


public class SessionProvider {

    private final EntityManagerFactory emFactory;

    public SessionProvider() {
        this.emFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    <T> T executeEntityManager(Function<EntityManager, T> _function){
        EntityManager em = emFactory.createEntityManager();
        try {
            return _function.apply(em);

        } finally {
            em.close();

        }
    }

    void executeInTtansaction(Consumer<EntityManager> _counsumer){
        EntityManager em = emFactory.createEntityManager();
        try {
            em.getTransaction().begin();

            _counsumer.accept(em);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
