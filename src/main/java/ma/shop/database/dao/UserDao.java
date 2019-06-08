package ma.shop.database.dao;

import ma.shop.database.model.Good;
import ma.shop.database.model.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    Optional<User> containce(String email);
    boolean addToOrder(Good good, User user);
    boolean clearOrder(User user);
}
