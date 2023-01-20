package model;

public class Order {
    private int id;
    private int client_id;
    private int product_id;
    private int quantity;
    private float total_price;

    public Order(int id, int client_id,  int product_id, int quantity, float total_price){
        this.id = id;
        this.client_id = client_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.total_price = total_price;
    }

    public Order(int client_id, int product_id, int quantity, float total_price){
        this.client_id = client_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.total_price = total_price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public int getId() {
        return id;
    }

    public int getClient_id() {
        return client_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getTotal_price() {
        return total_price;
    }
}
