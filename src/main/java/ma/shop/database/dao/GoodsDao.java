package ma.shop.database.dao;

import ma.shop.database.model.Good;

import java.util.List;
import java.util.Optional;

public interface GoodsDao {
    boolean addGood(Good good);
    boolean deleteGoodById(long id);
    Optional<Good> getGoodById(long id);
    List<Good> getGoods();
    boolean updateGood(long id, Good good);
}
