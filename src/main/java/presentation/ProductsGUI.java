package presentation;

import businessLayer.ProductBLL;
import model.Product;

import javax.swing.*;

public class ProductsGUI extends JFrame {
    private JTextField textProductName;
    private JTextField textProductQuantity;
    private JTextField textProductPrice;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton viewAllButton;
    private JTable tableProducts;
    private JButton searchButton;
    private JTextField textID;
    private JPanel mainProductsPane;

    String productName;
    int productQuantity;
    float productPrice;

    public ProductsGUI() {
        setContentPane(mainProductsPane);
        setTitle("Main Products Window");
        setBounds(600, 200, 600, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        //works fine
        saveButton.addActionListener(e -> {
            productName = textProductName.getText();
            productQuantity = Integer.parseInt(textProductQuantity.getText());
            productPrice = Float.parseFloat(textProductPrice.getText());

            if (productQuantity < 0) {
                JOptionPane.showMessageDialog(null, "Quantity can't be smaller than 0!");
                textProductQuantity.requestFocus();
            } else if (productPrice < 0) {
                JOptionPane.showMessageDialog(null, "Price can't be smaller than 0!");
                textProductPrice.requestFocus();
            } else {
                ProductBLL productBLL = new ProductBLL();
                Product product = new Product(productName, productQuantity, productPrice);
                productBLL.insertProduct(product);
                JOptionPane.showMessageDialog(null, "Product Saved!");
                textProductName.setText("");
                textProductQuantity.setText("");
                textProductPrice.setText("");
                textProductName.requestFocus();
                viewAllButton.doClick();
            }
        });

        //works fine
        updateButton.addActionListener(e -> {
            ProductBLL productBLL = new ProductBLL();
            int id = Integer.parseInt(textID.getText());
            Product product = productBLL.findByID(id);
            if (product == null) {
                JOptionPane.showMessageDialog(null, "Product with id:" + id + "does not exist!");
            } else {
                productName = textProductName.getText();
                productQuantity = Integer.parseInt(textProductQuantity.getText());
                productPrice = Float.parseFloat(textProductPrice.getText());
                if (productQuantity < 0) {
                    JOptionPane.showMessageDialog(null, "Quantity can't be smaller than 0!");
                    textProductQuantity.requestFocus();
                } else if (productPrice < 0) {
                    JOptionPane.showMessageDialog(null, "Price can't be smaller than 0!");
                    textProductPrice.requestFocus();
                } else {
                    product.setName(productName);
                    product.setQuantity(productQuantity);
                    product.setPrice(productPrice);
                    productBLL.update(product);
                    JOptionPane.showMessageDialog(null, "Product Updated!");
                    textProductName.setText("");
                    textProductQuantity.setText("");
                    textProductPrice.setText("");
                    viewAllButton.doClick();
                }
            }
        });

        //works fine
        deleteButton.addActionListener(e -> {
            ProductBLL productBLL = new ProductBLL();
            int id = Integer.parseInt(textID.getText());
            Product product = productBLL.findByID(id);
            if (product == null) {
                JOptionPane.showMessageDialog(null, "Product with id:" + id + " was not found");
            } else {
                productBLL.delete(id);
                JOptionPane.showMessageDialog(null, "Product Deleted!");
                textProductName.setText("");
                textProductQuantity.setText("");
                textProductPrice.setText("");
                viewAllButton.doClick();
            }
        });

        //works fine
        viewAllButton.addActionListener(e -> {
            ProductBLL productBLL = new ProductBLL();
            tableProducts.setModel(productBLL.viewAllProducts());
        });

        //works fine
        searchButton.addActionListener(e -> {
            ProductBLL productBLL = new ProductBLL();
            int id = Integer.parseInt(textID.getText());
            Product product = productBLL.findByID(id);
            if (product != null) {
                textProductName.setText(product.getName());
                textProductQuantity.setText(Integer.toString(product.getQuantity()));
                textProductPrice.setText(Float.toString(product.getPrice()));
            } else {
                JOptionPane.showMessageDialog(null, "Product not found!");
            }
        });
    }
}
