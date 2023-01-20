package dataAccessLayer;

import connection.ConnectionFactory;
import model.Product;

import javax.swing.table.TableModel;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDAO extends AbstractDAO<Product> {
    protected final static Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    private final static String insertStatementString = "INSERT INTO product (name,quantity,price)" + " VALUES(?,?,?)";
    private final static String findStatementString = "SELECT * FROM product WHERE id = ?";
    private final static String findAllStatementString = "SELECT * FROM product";
    private final static String updateStatementString = "UPDATE product SET name=?, quantity=?, price=?" + " WHERE id=?";
    private final static String deleteStatementString = "DELETE FROM product WHERE id=?";

    public static TableModel findAll() {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findAllStatement = null;
        ResultSet rs = null;
        List<Product> products = new LinkedList<>();
        try {
            findAllStatement = dbConnection.prepareStatement(findAllStatementString);
            rs = findAllStatement.executeQuery();
            while (rs.next()) {
                Product product = new Product(rs.getInt("id"), rs.getString("name"), rs.getInt("quantity"), rs.getInt("price"));
                products.add(product);
            }
            return createTableModel(products);
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(dbConnection);
            ConnectionFactory.close(findAllStatement);
        }
        return null;
    }

    public static Product findByID(int productID) {
        Product toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;

        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setInt(1, productID);
            rs = findStatement.executeQuery();
            rs.next();

            String name = rs.getString("name");
            int quantity = rs.getInt("quantity");
            float price = rs.getFloat("price");
            toReturn = new Product(productID, name, quantity, price);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO: findByID" + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static int insert(Product product) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;
        int insertedId = -1;

        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, product.getName());
            insertStatement.setInt(2, product.getQuantity());
            insertStatement.setFloat(3, product.getPrice());

            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO: insert" + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    public static void update(Product product) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;
        try {
            updateStatement = dbConnection.prepareStatement(updateStatementString);
            updateStatement.setString(1, product.getName());
            updateStatement.setInt(2, product.getQuantity());
            updateStatement.setFloat(3, product.getPrice());
            updateStatement.setInt(4, product.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO: update" + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(dbConnection);
        }
    }

    public static void delete(int id) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO: delete" + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
    }
}


