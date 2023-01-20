package model;

public class Client {
    private int id;
    private String name;
    private int age;
    private String telephone;

    public Client(int id, String name, int age, String telephone){
        this.id = id;
        this.name = name;
        this.age = age;
        this.telephone = telephone;
    }

    public Client(String name, int age, String telephone) {
        this.name = name;
        this.age = age;
        this.telephone = telephone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getTelephone() {
        return telephone;
    }

}
