package ma.shop.database.dao.impl;

import ma.shop.database.dao.GenericDao;
import ma.shop.utils.HibernateSessionFactoryUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenericDaoHiberImpl<T> implements GenericDao<T> {
    private Logger logger = Logger.getLogger(GenericDaoHiberImpl.class);

    Class<T> clazz;

    public GenericDaoHiberImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean add(T object) {
        try (Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();

            logger.debug("Add " + clazz.getCanonicalName());
            return true;
        } catch (Exception e) {
            logger.error("Error added " + object.getClass().getCanonicalName());
            return false;
        }
    }

    @Override
    public boolean deleteById(long id) {
        try (Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()) {
            Transaction transaction = session.beginTransaction();
            T object = session.get(clazz, id);
            session.delete(object);
            transaction.commit();

            logger.debug("Delete " + clazz.getCanonicalName() + " by id=" + id);
            return true;
        } catch (Exception e) {
            logger.error("Error delete " + clazz.getCanonicalName() + " by id=" + id, e);
            return false;
        }
    }

    @Override
    public Optional<T> getById(long id) {
        try (Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()) {
            T object = session.get(clazz, id);

            logger.debug("Get " + clazz.getCanonicalName() + " by id=" + id);
            return Optional.ofNullable(object);
        } catch (Exception e) {
            logger.error("Error get " + clazz.getCanonicalName() + " by id=" + id, e);
            return Optional.empty();
        }
    }

    @Override
    public List<T> getAll() {
        try (Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()) {
            List<T> objects = session
                    .createQuery("From " + clazz.getName())
                    .list();

            logger.debug("Get all " + clazz.getCanonicalName() + "'s");
            return objects;
        } catch (Exception e) {
            logger.error("Error get all  " + clazz.getCanonicalName(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public boolean update(T object) {
        try (Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(object);
            transaction.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
