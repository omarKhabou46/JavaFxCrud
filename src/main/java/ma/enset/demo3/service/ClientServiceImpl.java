package ma.enset.demo3.service;

import ma.enset.demo3.dao.ClientDao;
import ma.enset.demo3.entity.Client;

import java.sql.SQLException;
import java.util.List;

public class ClientServiceImpl implements ClientService{
    private ClientDao clientDao;

    public ClientServiceImpl(ClientDao clDao) {
        this.clientDao = clDao;
    }
    @Override
    public List<Client> fetchClients() throws SQLException {
        return clientDao.getAllClient();
    }

    @Override
    public void addClient(Client cl) throws SQLException {
        clientDao.createClient(cl);
    }

    @Override
    public void delete(Long id) throws SQLException{
        clientDao.deleteById(id);
    }

    @Override
    public void updateClient(Client cl) throws SQLException{
        clientDao.updateClient(cl);
    }
}
