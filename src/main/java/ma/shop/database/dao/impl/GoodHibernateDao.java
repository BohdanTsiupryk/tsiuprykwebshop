package ma.shop.database.dao.impl;

import ma.shop.database.dao.GoodsDao;
import ma.shop.database.model.Good;

public class GoodHibernateDao extends GenericDaoHiberImpl<Good> implements GoodsDao {

    public GoodHibernateDao() {
        super(Good.class);
    }
}
