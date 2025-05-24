package ma.enset.demo3.service;

import ma.enset.demo3.entity.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientService {
    List<Client> fetchClients() throws SQLException;
    void addClient(Client cl) throws SQLException;
    void delete(Long id) throws SQLException;
    void updateClient(Client cl) throws SQLException;
}
