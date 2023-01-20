package dataAccessLayer;

import connection.ConnectionFactory;
import model.Client;

import javax.swing.table.TableModel;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientDAO extends AbstractDAO<Client> {
    protected final static Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    private final static String insertStatementString = "INSERT INTO client (name,age,telephone)" + " VALUES(?,?,?)";
    private final static String findStatementString = "SELECT * FROM client WHERE id = ?";
    private final static String findAllStatementString = "SELECT * FROM client";
    private final static String updateStatementString = "UPDATE client SET name=?, age=?, telephone=?" + " WHERE id=?";
    private final static String deleteStatementString = "DELETE FROM client WHERE id=?";

    /*
     * This method shows all the clients
     * */
    public static TableModel findAll() {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findAllStatement = null;
        ResultSet rs = null;
        List<Client> clients = new LinkedList<>();
        try {
            findAllStatement = dbConnection.prepareStatement(findAllStatementString);
            rs = findAllStatement.executeQuery();
            while (rs.next()) {
                Client client = new Client(rs.getInt("id"), rs.getString("name"), rs.getInt("age"), rs.getString("telephone"));
                clients.add(client);
            }
            return createTableModel(clients);
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(dbConnection);
            ConnectionFactory.close(findAllStatement);
        }
        return null;
    }

    /**
     * @param clientID the id of the search client
     * @return a client object
     */
    public static Client findById(int clientID) {
        Client toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;

        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setInt(1, clientID);
            rs = findStatement.executeQuery();
            rs.next(); //to make the first row current row

            String name = rs.getString("name");
            int age = rs.getInt("age");
            String telephone = rs.getString("telephone");
            toReturn = new Client(clientID, name, age, telephone);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO: findById" + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static int insert(Client client) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, client.getName());
            insertStatement.setInt(2, client.getAge());
            insertStatement.setString(3, client.getTelephone());

            insertStatement.executeUpdate(); //execute statement

            ResultSet rs = insertStatement.getGeneratedKeys(); //return id of inserted client
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO: insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    public static void update(Client client) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;
        try {
            updateStatement = dbConnection.prepareStatement(updateStatementString);
            updateStatement.setString(1, client.getName());
            updateStatement.setInt(2, client.getAge());
            updateStatement.setString(3, client.getTelephone());
            updateStatement.setInt(4, client.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO: update " + e.getMessage());
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
            LOGGER.log(Level.WARNING, "ClientDAO: delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
    }
}
