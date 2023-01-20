package presentation;

import businessLayer.ClientBLL;
import businessLayer.OrderBLL;
import businessLayer.ProductBLL;
import model.Order;
import model.Product;

import javax.swing.*;

public class OrdersGUI extends JFrame {
    private JPanel mainPanel;
    private JTextField textClientID;
    private JTextField textProductID;
    private JTextField textQuantity;
    private JTable tableOrders;
    private JButton saveButton;
    private JButton viewAllButton;

    private int clientID;
    private int productID;
    private int quantity;
    private float totalPrice;
    private int availableQuantity;
    private float productPrice;

    public OrdersGUI() {
        setContentPane(mainPanel);
        setTitle("Main Orders Window");
        setBounds(600,200,600,300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        saveButton.addActionListener(e -> {
            clientID = Integer.parseInt(textClientID.getText());
            productID = Integer.parseInt(textProductID.getText());
            quantity = Integer.parseInt(textQuantity.getText());
            ProductBLL productBLL = new ProductBLL();
            Product product = productBLL.findByID(productID);
            OrderBLL orderBLL = new OrderBLL();
            availableQuantity = product.getQuantity();
            productPrice = product.getPrice();

            if(quantity > availableQuantity){
                JOptionPane.showMessageDialog(null, "Sorry, we do not have enough on stock right now!");
            } else {
                //TODO: decrement availableQuantity and save
                totalPrice = quantity * productPrice;
                Order order = new Order(clientID,productID,quantity, totalPrice);
                orderBLL.insertOrder(order);
                availableQuantity = availableQuantity - quantity;
                product.setQuantity(availableQuantity);
                productBLL.update(product);
                JOptionPane.showMessageDialog(null, "Order successfully placed!");
            }
        });


        viewAllButton.addActionListener(e -> {
            OrderBLL orderBLL = new OrderBLL();
            tableOrders.setModel(orderBLL.viewAllOrders());
        });

    }
}
