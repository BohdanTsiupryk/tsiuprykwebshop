package ma.shop.database.dao;

import ma.shop.database.connector.DbConnector;
import ma.shop.database.model.Good;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DatabaseGoodDao implements GoodsDao {
    private static final Logger LOG = Logger.getLogger(DatabaseGoodDao.class);

    @Override
    public boolean addGood(Good good) {
        String addSql = "INSERT INTO goods (name, description, price) VALUES (?,?,?)";
        try (Connection connection = DbConnector.getConnection();
             PreparedStatement statementAdd = connection.prepareStatement(addSql)) {

            LOG.debug("Add good sql: " + addSql);
            LOG.debug("Try add good with name-" + good.getName());

            statementAdd.setString(1, good.getName());
            statementAdd.setString(2, good.getDescription());
            statementAdd.setDouble(3, good.getPrice());

            if (statementAdd.execute()) {
                LOG.info("Successful add new good, name-" + good.getName());
                return true;
            } else {
                LOG.info("Fail to add good with name-" + good.getName());
                return false;
            }
        } catch (SQLException e) {
            LOG.error("Can't add good to DB", e);
        }

        return false;
    }

    @Override
    public boolean deleteGoodById(long id) {
        String deleteSql = "DELETE FROM goods WHERE goods.id = " + id;

        try (Connection connection = DbConnector.getConnection();
             Statement statementDelete = connection.createStatement()) {
            int update = statementDelete.executeUpdate(deleteSql);

            if (update == 1) {
                LOG.info("Successful delete good with id=" + id);
                return true;
            } else if (update == 0) {
                LOG.info("Cannot find good with id = " + id);
                return false;
            } else if (update > 1) {
                LOG.error("Multiply deleting !!!");
                throw new SQLException("Multiply deleting !!!");
            }

        } catch (SQLException e) {
            LOG.error("Cannot delete good. ", e);
        }
        return false;

    }

    @Override
    public Optional<Good> getGoodById(long id) {
        String getOneSql = "SELECT id, name, description, price FROM goods WHERE id = " + id;

        try (Connection connection = DbConnector.getConnection();
             Statement statementGetOne = connection.createStatement()) {

            ResultSet resultSet = statementGetOne.executeQuery(getOneSql);

            if (resultSet.next()) {
                long resId = resultSet.getLong("id");
                String resName = resultSet.getString("name");
                String resDescription = resultSet.getString("description");
                double resDouble = resultSet.getDouble("price");

                return Optional.of(new Good(resId, resName, resDescription, resDouble));
            } else {
                LOG.error("Cannot find good with id = " + id);
                return Optional.empty();
            }
        } catch (SQLException e) {
            LOG.error("Cannot delete good. ", e);
        }
        return Optional.empty();    
    }

    @Override
    public List<Good> getGoods() {
        String getAllSql = "SELECT * FROM goods";
        List<Good> goods = new ArrayList<>();

        try (Connection connection = DbConnector.getConnection();
             Statement statementGetOne = connection.createStatement()) {

            ResultSet resultSet = statementGetOne.executeQuery(getAllSql);

            while (resultSet.next()) {
                goods.add(new Good(resultSet.getLong("id"), resultSet.getString("name"),
                        resultSet.getString("description"), resultSet.getDouble("price")));
            }
        } catch (SQLException e) {
            LOG.error("Cannot delete good. ", e);
        }
        LOG.debug("Get all goods from db, size = " + goods.size());
        return goods;
    }

    @Override
    public boolean updateGood(long id, Good good) {
        String sqlUpdateGood = "UPDATE goods SET name = ?, description = ?, price = ? WHERE id = ?;";
        try (Connection connection = DbConnector.getConnection();
             PreparedStatement statementUpdate = connection.prepareStatement(sqlUpdateGood)) {

            statementUpdate.setString(1, good.getName());
            statementUpdate.setString(2, good.getDescription());
            statementUpdate.setDouble(3, good.getPrice());
            statementUpdate.setLong(5, good.getId());

            int updateRow = statementUpdate.executeUpdate();

            if (updateRow == 1) {
                LOG.info("Successful update good with id=" + id);
                return true;
            } else if (updateRow == 0) {
                LOG.info("Cannot find good with id=" + id);
            } else {
                LOG.error("ERROR in sql syntex");
            }
        } catch (SQLException e) {
            LOG.error("Cannot delete good. ", e);
        }
        return false;
    }
}
