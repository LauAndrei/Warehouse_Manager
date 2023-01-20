package businessLayer;

import dataAccessLayer.ProductDAO;
import model.Product;

import javax.swing.table.TableModel;

public class ProductBLL {

    public ProductBLL(){

    }

    public void insertProduct(Product product){
        ProductDAO.insert(product);
    }

    public TableModel viewAllProducts(){
        return ProductDAO.findAll();
    }

    public Product findByID(int id){
        return ProductDAO.findByID(id);
    }

    public void update(Product product){
        ProductDAO.update(product);
    }

    public void delete(int id){
        ProductDAO.delete(id);
    }
}
