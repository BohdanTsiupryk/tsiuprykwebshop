package ma.shop.database.dao.impl;

import ma.shop.database.dao.GenericDao;
import ma.shop.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenericDaoHiberImpl<T> implements GenericDao<T> {
    Class<T> clazz;

    public GenericDaoHiberImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean add(T object) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteById(long id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            T object = session.get(clazz, id);
            session.delete(object);
            transaction.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Optional<T> getById(long id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            T object = session.get(clazz, id);
            return Optional.ofNullable(object);
        } catch (Exception e) {
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
            return objects;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean update(T object) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(object);
            transaction.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
