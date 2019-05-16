package ma.shop.database.dao;

import ma.shop.database.model.Good;
import ma.shop.database.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    boolean addUser(User user);
    boolean deleteUserById(long id);
    Optional<User> getUserById(long id);
    List<User> getUsers();
    boolean updateUser(long id, User user);
    Optional<User> containce(String email);
    boolean containceEmail(String email);
    boolean addGood(Good good, User user);
    boolean removeGood(User user);
}
