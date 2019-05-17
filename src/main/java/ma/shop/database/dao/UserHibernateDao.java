package ma.shop.database.dao;

import ma.shop.database.model.Good;
import ma.shop.database.model.User;
import ma.shop.servlets.LoginServlet;
import ma.shop.utils.HibernateSessionFactoryUtil;
import ma.shop.utils.SHA512SecureUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class UserHibernateDao implements UserDao {
    private static final Logger log = Logger.getLogger(UserHibernateDao.class);

    @Override
    public boolean addUser(User user) {
        user.setPassword(SHA512SecureUtil.getSecurePassword(user.getPassword(), user.getSalt()));
        Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
        return true;
    }

    @Override
    public boolean deleteUserById(long id) {
        Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession();
        Transaction tx1 = session.beginTransaction();
        User user = session.get(User.class, id);
        session.delete(user);
        tx1.commit();
        session.close();
        return true;
    }

    @Override
    public Optional<User> getUserById(long id) {
        User user = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()
                .get(User.class, id);

        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getUsers() {
        List<User> users = (List<User>) HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()
                .createQuery("From User")
                .list();
        return users;
    }

    @Override
    public boolean updateUser(long id, User user) {
        Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
        return true;
    }

    @Override
    public Optional<User> containce(String email) {
        User user = (User) HibernateSessionFactoryUtil.getSessionFactory()
                .openSession()
                .createQuery("From User WHERE email = :email")
                .setParameter("email", email)
                .uniqueResult();

        log.info(user.getEmail());
        return Optional.ofNullable(user);
    }

    @Override
    public boolean containceEmail(String email) {
        Optional<User> containce = containce(email);
        return containce.isPresent();
    }

    @Override
    public boolean addGood(Good good, User user) {
        user.setGood((int) good.getId());

        return updateUser(user.getId(), user);
    }

    @Override
    public boolean removeGood(User user) {
        Optional<User> userById = getUserById(user.getId());
        if (userById.isPresent()) {
            User user1 = userById.get();
            user1.setGood(0);
            updateUser(user1.getId(), user1);
            return true;
        }
        return false;
    }
}
