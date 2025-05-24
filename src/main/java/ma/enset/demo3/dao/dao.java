package ma.enset.demo3.dao;

import ma.enset.demo3.entity.Client;

import java.sql.SQLException;
import java.util.List;

public interface dao {
    List<Client> getAllClient() throws SQLException;
    void createClient(Client cl) throws SQLException;
    void deleteById(Long id) throws SQLException;
    void updateClient(Client cl) throws SQLException;
}
