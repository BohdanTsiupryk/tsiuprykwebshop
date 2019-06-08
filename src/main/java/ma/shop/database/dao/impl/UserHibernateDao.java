package ma.shop.database.dao.impl;

import ma.shop.database.dao.UserDao;
import ma.shop.database.model.Good;
import ma.shop.database.model.User;
import ma.shop.utils.HibernateSessionFactoryUtil;
import org.apache.log4j.Logger;

import java.util.Optional;

public class UserHibernateDao extends GenericDaoHiberImpl<User> implements UserDao {
    private static final Logger log = Logger.getLogger(UserHibernateDao.class);

    public UserHibernateDao() {
        super(User.class);
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
    public boolean addToOrder(Good good, User user) {
        user.getOrder().getGoods().add(good);

        return this.update(user);
    }

    @Override
    public boolean clearOrder(User user) {
        user.getOrder().getGoods().clear();

        return this.update(user);
    }
}
