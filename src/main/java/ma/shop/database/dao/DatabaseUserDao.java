package ma.shop.database.dao;

import ma.shop.database.connector.DbConnector;
import ma.shop.database.model.Good;
import ma.shop.database.model.Role;
import ma.shop.database.model.User;
import ma.shop.utils.SHA512SecureUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DatabaseUserDao implements UserDao {

    private static final Logger LOG = Logger.getLogger(DatabaseUserDao.class);
    private static String sqlInnerRole = "INNER JOIN roles ON role = roles.id ";

    @Override
    public boolean addUser(User user) {
        String addSql = "INSERT INTO users (email, password, address, role, salt) VALUES (?,?,?,?,?)";
        try (Connection connection = DbConnector.getConnection();
             PreparedStatement statementAdd = connection.prepareStatement(addSql)) {

            LOG.debug("Add user sql: " + addSql);
            LOG.debug("Try add user with email-" + user.getEmail());

            statementAdd.setString(1, user.getEmail());
            statementAdd.setString(2, SHA512SecureUtil.getSecurePassword(user.getPassword(), user.getSalt()));
            statementAdd.setString(3, user.getAddress());
            statementAdd.setString(5, user.getSalt());
            statementAdd.setInt(4, user.getRole().getId());

            if (statementAdd.execute()) {
                LOG.info("Successful add new user, email-" + user.getEmail());
                return true;
            } else {
                LOG.info("No successful try to add user with the no unic email-" + user.getEmail());
                return false;
            }
        } catch (SQLException e) {
            LOG.error("Can't add user to DB", e);
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
                LOG.info("Successful delete user with id=" + id);
                return true;
            } else if (update == 0) {
                LOG.info("Cannot find user with id = " + id);
                return false;
            } else if (update > 1) {
                LOG.error("Multiply deleting !!!");
                throw new SQLException("Multiply deleting !!!");
            }

        } catch (SQLException e) {
            LOG.error("Cannot delete user. ", e);
        }
        return false;
    }

    @Override
    public Optional<User> getUserById(long id) {
        String getOneSql = "SELECT users.id, email, password, address, good, role_name, salt FROM users " +
                sqlInnerRole + "WHERE users.id = " + id;

        try (Connection connection = DbConnector.getConnection();
             Statement statementGetOne = connection.createStatement()) {

            ResultSet resultSet = statementGetOne.executeQuery(getOneSql);

            if (resultSet.next()) {
                return getUser(resultSet);

            } else {
                LOG.error("Cannot find user with id = " + id);
                return Optional.empty();
            }
        } catch (SQLException e) {
            LOG.error("Cannot delete user. ", e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> getUsers() {
        String getAllSql = "SELECT users.id, email, password, address, role_name FROM users " + sqlInnerRole;
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
            LOG.error("Cannot get users. ", e);
        }
        LOG.debug("Get all users from db, size = " + users.size());
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
            LOG.error("Cannot update user. ", e);
        }
        return false;
    }

    @Override
    public Optional<User> containce(String email) {
        String containceSql = "SELECT users.id, email, password, address, good, role_name, salt FROM users " + sqlInnerRole + " WHERE email = ?";

        try (Connection connection = DbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(containceSql)) {

            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            LOG.debug("Check user with email-" + email);

            if (resultSet.next()) {
                LOG.debug("User with email-" + email + " is EXIST");
                return getUser(resultSet);
            }
            LOG.debug("User with email-" + email + " NOT EXIST");
        } catch (SQLException e) {
            LOG.error("Conteince exception", e);
        }
        return Optional.empty();
    }

    private Optional<User> getUser(ResultSet resultSet) throws SQLException {
        long resId = resultSet.getLong("id");
        String resEmail = resultSet.getString("email");
        String resAddress = resultSet.getString("address");
        String resPassword = resultSet.getString("password");
        String resSalt = resultSet.getString("salt");
        int good = resultSet.getInt("good");
        Role resRole = Role.valueOf(resultSet.getString("role_name"));

        return Optional.of(new User(resId, resEmail, resPassword, resAddress, good, resRole, resSalt));
    }

    @Override
    public boolean containceEmail(String email) {
        String containceSql = "SELECT * FROM users WHERE email = ?";

        try (Connection connection = DbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(containceSql)) {

            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            LOG.debug("Check user with email-" + email);

            if (resultSet.next()) {
                LOG.debug("User with email-" + email + " is EXIST");
                return true;
            }
            LOG.debug("User with email-" + email + " not EXIST");
        } catch (SQLException e) {
            LOG.error("Conteince exception", e);
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
            LOG.error("Cannot update user. ", e);
        }
        return false;
    }

    private boolean updateQuery(PreparedStatement statementUpdate, long id) throws SQLException {
        int updateRow = statementUpdate.executeUpdate();

        if (updateRow == 1) {
            LOG.info("Successful update user with id=" + id);
            return true;
        } else if (updateRow == 0) {
            LOG.info("Cannot find user with id=" + id);
        } else {
            LOG.error("ERROR in sql syntex");
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
            LOG.error("Cannot delete good. ", e);
        }
        return false;
    }
}
