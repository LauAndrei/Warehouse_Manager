package presentation;

import connection.ConnectionFactory;

import javax.swing.*;
import java.sql.Connection;

public class MainGUI extends JFrame{
    private JPanel mainPanel;
    private JButton clientsButton;
    private JButton ordersButton;
    private JButton productsButton;

    public MainGUI(){
        setContentPane(mainPanel);
        setTitle("Main Management Window");
        setBounds(600,200,400,150);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        clientsButton.addActionListener(e->{
            new ClientsGUI();
        });

        productsButton.addActionListener(e -> {
            new ProductsGUI();
        });

        ordersButton.addActionListener(e -> {
            new OrdersGUI();
        });
    }

    public static void main(String[] args) {
        Connection connection = ConnectionFactory.getConnection();
        new MainGUI();
    }
}
