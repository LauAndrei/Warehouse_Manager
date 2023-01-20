package presentation;

import businessLayer.ClientBLL;
import model.Client;

import javax.swing.*;

public class ClientsGUI extends JFrame {
    private JPanel mainClientsPanel;
    private JTextField textClientName;
    private JTextField textClientAge;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField textID;
    private JTextField textPhoneNumber;
    private JButton viewAllButton;
    private JScrollPane scrollPane;
    private JTable tableClients;

    private String clientName;
    private int clientAge;
    private String clientPhoneNumber;

    public ClientsGUI() {
        setContentPane(mainClientsPanel);
        setTitle("Main Clients Window");
        setBounds(600, 200, 600, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        //works well
        saveButton.addActionListener(e -> {
            clientName = textClientName.getText();
            clientAge = Integer.parseInt(textClientAge.getText());
            clientPhoneNumber = textPhoneNumber.getText();

            if (clientAge < 18) {
                JOptionPane.showMessageDialog(null, "Sorry, client is too young!");
                textClientAge.requestFocus();
            } else if (!clientPhoneNumber.matches("\\+?[0-9]*")) {
                JOptionPane.showMessageDialog(null, "Invalid Number Format!");
            } else {
                Client client = new Client(clientName, clientAge, clientPhoneNumber);
                ClientBLL clientBLL = new ClientBLL();
                clientBLL.insertClient(client);
                JOptionPane.showMessageDialog(null, "Client Saved!");
                textClientName.setText("");
                textClientAge.setText("");
                textPhoneNumber.setText("");
                textClientName.requestFocus();
                viewAllButton.doClick();
            }
        });

        //works well
        updateButton.addActionListener(e -> {
            ClientBLL clientBLL = new ClientBLL();
            int id = Integer.parseInt(textID.getText());
            Client client = clientBLL.findByID(id);
            if (client != null) {
                clientName = textClientName.getText();
                clientAge = Integer.parseInt(textClientAge.getText());
                clientPhoneNumber = textPhoneNumber.getText();
                if (clientAge < 18) {
                    JOptionPane.showMessageDialog(null, "Sorry, client is too young!");
                } else if (!clientPhoneNumber.matches("\\+?[0-9]*")) {
                    JOptionPane.showMessageDialog(null, "Invalid Number Format!");
                } else {
                    client.setName(clientName);
                    client.setAge(clientAge);
                    client.setTelephone(clientPhoneNumber);
                    clientBLL.update(client);
                    JOptionPane.showMessageDialog(null, "Client Updated!");
                    textClientName.setText("");
                    textClientAge.setText("");
                    textPhoneNumber.setText("");
                    viewAllButton.doClick();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Client with id: " + id + " does not exist!");
            }
        });


        deleteButton.addActionListener(e -> {
            ClientBLL clientBLL = new ClientBLL();
            int id = Integer.parseInt(textID.getText());
            Client client = clientBLL.findByID(id);
            if (client == null) {
                JOptionPane.showMessageDialog(null, "Client with id:" + id + " was not found!");
            } else {
                clientBLL.delete(id);
                    JOptionPane.showMessageDialog(null, "Client Deleted!");
                textClientName.setText("");
                textClientAge.setText("");
                textPhoneNumber.setText("");
                viewAllButton.doClick();
            }
        });

        //works well
        viewAllButton.addActionListener(e -> {
            ClientBLL clientBLL = new ClientBLL();
            tableClients.setModel(clientBLL.viewAllClients());
        });

        //works well
        searchButton.addActionListener(e -> {
            ClientBLL clientBLL = new ClientBLL();
            int id = Integer.parseInt(textID.getText());
            Client client = clientBLL.findByID(id);
            if (client != null) {
                textClientName.setText(client.getName());
                textClientAge.setText(Integer.toString(client.getAge()));
                textPhoneNumber.setText(client.getTelephone());
            } else {
                JOptionPane.showMessageDialog(null, "Client with id: " + id + " was not found!");
            }
        });
    }
}
