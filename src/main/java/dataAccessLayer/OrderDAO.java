package dataAccessLayer;

import com.mysql.cj.log.Log;
import connection.ConnectionFactory;
import model.Order;

import javax.swing.table.TableModel;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAO extends AbstractDAO<Order> {
    protected final static Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    private final static String insertStatementString = "INSERT INTO orders (client_id, product_id, quantity, total_price)" + " VALUES(?,?,?,?)";
    private final static String findAllStatementString = "SELECT * FROM orders";

    public static TableModel findAll() {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findAllStatement = null;
        ResultSet rs = null;
        List<Order> orders = new LinkedList<>();
        try {
            findAllStatement = dbConnection.prepareStatement(findAllStatementString);
            rs = findAllStatement.executeQuery();
            while (rs.next()) {
                Order order = new Order(rs.getInt("id"), rs.getInt("client_id"), rs.getInt("product_id"), rs.getInt("quantity"), rs.getFloat("total_price"));
                orders.add(order);
            }
            return createTableModel(orders);
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, "OrderDao: findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(dbConnection);
            ConnectionFactory.close(findAllStatement);
        }
        return null;
    }


    public static int insert(Order order){
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;
        int insertedID = -1;
        try{
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, order.getClient_id());
            insertStatement.setInt(2, order.getProduct_id());
            insertStatement.setInt(3, order.getQuantity());
            insertStatement.setFloat(4, order.getTotal_price());
            insertStatement.executeUpdate();
            ResultSet rs = insertStatement.getGeneratedKeys();

            if(rs.next()){
                insertedID = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO: insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedID;
    }

}
