package ma.enset.demo3.service;

import ma.enset.demo3.dao.ClientDaoImpl;
import ma.enset.demo3.entity.Client;

import java.sql.SQLException;
import java.util.List;

public class testService {

    public static void main(String[] args) throws SQLException {
        ClientService clientService = new ClientServiceImpl(new ClientDaoImpl());
        clientService.addClient(new Client("omar", "khabou", 20));
        clientService.addClient(new Client("ismail", "elfetouaki", 21));

        List<Client> clientList = clientService.fetchClients();
        for (Client cl : clientList) {
            System.out.println("id : " + cl.getId() + " lastName : " + cl.getLastName());
        }




    }


}
