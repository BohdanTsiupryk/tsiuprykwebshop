package ma.shop.database.dao;

import ma.shop.database.connector.DbConnector;
import ma.shop.database.model.Good;
import ma.shop.database.model.Role;
import ma.shop.database.model.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DatabaseUserDao implements UserDao {

    private static final Logger log = Logger.getLogger(DatabaseUserDao.class);
    private static String sqlInnerRole = "INNER JOIN roles ON role = roles.id ";

    @Override
    public boolean addUser(User user) {
        String addSql = "INSERT INTO users (email,password,address,role) VALUES (?,?,?,?)";
        try (Connection connection = DbConnector.getConnection();
             PreparedStatement statementAdd = connection.prepareStatement(addSql)) {

            log.debug("Add user sql: " + addSql);
            log.debug("Try add user with email-" + user.getEmail());

            statementAdd.setString(1, user.getEmail());
            statementAdd.setString(2, user.getPassword());
            statementAdd.setString(3, user.getAddress());
            statementAdd.setInt(4, user.getRole().getId());

            if (statementAdd.execute()) {
                log.info("Successful add new user, email-" + user.getEmail());
                return true;
            } else {
                log.info("No successful try to add user with the no unic email-" + user.getEmail());
                return false;
            }
        } catch (SQLException e) {
            log.error("Can't add user to DB", e);
        }

        return false;
    }

    @Override
    public boolean deleteUserById(long id) {
        String deleteSql = "DELETE FROM users WHERE id = " + id;

        try (Connection connection = DbConnector.getConnection();
             Statement statementDelete = connection.createStatement()) {
            int update = statementDelete.executeUpdate(deleteSql);

            if (update == 1) {
                log.info("Successful delete user with id=" + id);
                return true;
            } else if (update == 0) {
                log.info("Cannot find user with id = " + id);
                return false;
            } else if (update > 1) {
                log.error("Multiply deleting !!!");
                throw new SQLException("Multiply deleting !!!");
            }

        } catch (SQLException e) {
            log.error("Cannot delete user. ", e);
        }
        return false;
    }

    @Override
    public Optional<User> getUserById(long id) {
        String getOneSql = "SELECT users.id,email,password,address,good,role_name FROM users " +
                sqlInnerRole + "WHERE users.id = " + id;

        try (Connection connection = DbConnector.getConnection();
             Statement statementGetOne = connection.createStatement()) {

            ResultSet resultSet = statementGetOne.executeQuery(getOneSql);

            if (resultSet.next()) {
                return getUser(resultSet);

            } else {
                log.error("Cannot find user with id = " + id);
                return Optional.empty();
            }
        } catch (SQLException e) {
            log.error("Cannot delete user. ", e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> getUsers() {
        String getAllSql = "SELECT users.id,email,password,address,role_name FROM users " + sqlInnerRole;
        List<User> users = new ArrayList<>();

        try (Connection connection = DbConnector.getConnection();
             Statement statementGetOne = connection.createStatement()) {

            ResultSet resultSet = statementGetOne.executeQuery(getAllSql);

            while (resultSet.next()) {
                users.add(new User(resultSet.getLong("id"), resultSet.getString("email"),
                        resultSet.getString("password"), resultSet.getString("address"),
                        Role.valueOf(resultSet.getString("role_name"))));
            }
        } catch (SQLException e) {
            log.error("Cannot get users. ", e);
        }
        log.debug("Get all users from db, size = " + users.size());
        return users;
    }

    @Override
    public boolean updateUser(long id, User user) {
        String sqlUpdateUser = "UPDATE users SET email = ?, password = ?, address = ?, role = ? WHERE id = ?;";
        try (Connection connection = DbConnector.getConnection();
             PreparedStatement statementUpdate = connection.prepareStatement(sqlUpdateUser)) {

            statementUpdate.setString(1, user.getEmail());
            statementUpdate.setString(2, user.getPassword());
            statementUpdate.setString(3, user.getAddress());
            statementUpdate.setInt(4, user.getRole().getId());
            statementUpdate.setLong(5, id);

            if (updateQuery(statementUpdate, id)) return true;
        } catch (SQLException e) {
            log.error("Cannot update user. ", e);
        }
        return false;
    }

    @Override
    public Optional<User> containce(String email, String pass) {
        String containceSql = "SELECT users.id,email,password,address,good,role_name FROM users " + sqlInnerRole + " WHERE email = ? AND password = ?";

        try (Connection connection = DbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(containceSql)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, pass);

            ResultSet resultSet = preparedStatement.executeQuery();
            log.debug("Check user with email-" + email);

            if (resultSet.next()) {
                log.debug("User with email-" + email + " is EXIST");
                return getUser(resultSet);
            }
            log.debug("User with email-" + email + " NOT EXIST");
        } catch (SQLException e) {
            log.error("Conteince exception", e);
        }
        return Optional.empty();
    }

    private Optional<User> getUser(ResultSet resultSet) throws SQLException {
        long resId = resultSet.getLong("id");
        String resEmail = resultSet.getString("email");
        String resAddress = resultSet.getString("address");
        String resPassword = resultSet.getString("password");
        int good = resultSet.getInt("good");
        Role resRole = Role.valueOf(resultSet.getString("role_name"));

        return Optional.of(new User(resId, resEmail, resPassword, resAddress, good, resRole));
    }

    @Override
    public boolean containceEmail(String email) {
        String containceSql = "SELECT * FROM users WHERE email = ?";

        try (Connection connection = DbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(containceSql)) {

            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            log.debug("Check user with email-" + email);

            if (resultSet.next()) {
                log.debug("User with email-" + email + " is EXIST");
                return true;
            }
            log.debug("User with email-" + email + " not EXIST");
        } catch (SQLException e) {
            log.error("Conteince exception", e);
        }
        return false;
    }

    @Override
    public boolean addGood(Good good, User user) {
        String sqlUpdateUser = "UPDATE users SET good = ? WHERE id = ?;";
        try (Connection connection = DbConnector.getConnection();
             PreparedStatement statementUpdate = connection.prepareStatement(sqlUpdateUser)) {

            statementUpdate.setLong(1, good.getId());
            long id = user.getId();
            statementUpdate.setLong(2, id);

            if (updateQuery(statementUpdate, id)) {
                return true;
            }
        } catch (SQLException e) {
            log.error("Cannot update user. ", e);
        }
        return false;
    }

    private boolean updateQuery(PreparedStatement statementUpdate, long id) throws SQLException {
        int updateRow = statementUpdate.executeUpdate();

        if (updateRow == 1) {
            log.info("Successful update user with id=" + id);
            return true;
        } else if (updateRow == 0) {
            log.info("Cannot find user with id=" + id);
        } else {
            log.error("ERROR in sql syntex");
        }
        return false;
    }

    @Override
    public boolean removeGood(User user) {
        String sqlUpdateUser = "UPDATE users SET good = NULL WHERE id = ?;";
        try (Connection connection = DbConnector.getConnection();
             PreparedStatement statementUpdate = connection.prepareStatement(sqlUpdateUser)) {
            statementUpdate.setLong(1, user.getId());

            if (updateQuery(statementUpdate, user.getId())) {
                return true;
            }
        } catch (SQLException e) {
            log.error("Cannot delete good. ", e);
        }
        return false;
    }
}
