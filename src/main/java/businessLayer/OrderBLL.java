package businessLayer;

import dataAccessLayer.OrderDAO;
import model.Order;

import javax.swing.table.TableModel;

public class OrderBLL {

    public OrderBLL(){

    }

    public void insertOrder(Order order){
        OrderDAO.insert(order);
    }

    public TableModel viewAllOrders(){
        return OrderDAO.findAll();
    }

}
