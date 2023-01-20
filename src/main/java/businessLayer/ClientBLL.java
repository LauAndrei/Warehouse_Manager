package businessLayer;

import dataAccessLayer.ClientDAO;
import model.Client;

import javax.swing.table.TableModel;

public class ClientBLL {

    public ClientBLL() {

    }

    public void insertClient(Client client) {
        ClientDAO.insert(client);
    }

    public TableModel viewAllClients() {
        return ClientDAO.findAll();
    }

    public Client findByID(int id){
        return ClientDAO.findById(id);
    }

    public void update(Client client){
        ClientDAO.update(client);
    }

    public void delete(int id){
        ClientDAO.delete(id);
    }
}
