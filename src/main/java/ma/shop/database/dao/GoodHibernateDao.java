package ma.shop.database.dao;

import ma.shop.database.model.Good;
import ma.shop.database.model.User;
import ma.shop.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class GoodHibernateDao implements GoodsDao {
    @Override
    public boolean addGood(Good good) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(good);
        tx1.commit();
        session.close();
        return true;
    }

    @Override
    public boolean deleteGoodById(long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Good good = session.get(Good.class, id);
        session.delete(good);
        tx1.commit();
        session.close();
        return true;
    }

    @Override
    public Optional<Good> getGoodById(long id) {
        Good good = HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Good.class, id);

        return Optional.ofNullable(good);
    }

    @Override
    public List<Good> getGoods() {
        List<Good> goods = (List<Good>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Good").list();
        return goods;
    }

    @Override
    public boolean updateGood(long id, Good good) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(good);
        tx1.commit();
        session.close();
        return true;
    }
}
