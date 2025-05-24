package ma.enset.demo3.dao;

import ma.enset.demo3.entity.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoImpl implements ClientDao {
    private Connection connection = DbConnection.getConnection();
    List<Client> clients = null;
    public List<Client> getAllClient() throws SQLException {
        clients = new ArrayList<>();
        Statement stm = connection.createStatement();
        ResultSet res = stm.executeQuery("select * from client");

        while (res.next()) {
            Client cl = new Client();
            cl.setId(res.getLong("id"));
            cl.setFirstName(res.getString("firstName"));
            cl.setLastName(res.getString("lastName"));
            cl.setAge(res.getInt("age"));
            clients.add(cl);
        }
        return clients;
    }

    public void createClient(Client cl) throws SQLException{
        PreparedStatement stm = connection.prepareStatement("insert into client(firstName, lastName, age) values (?, ?, ?)");
        stm.setString(1, cl.getFirstName());
        stm.setString(2, cl.getLastName());
        stm.setInt(3, cl.getAge());
        stm.executeUpdate();
    }

    @Override
    public void deleteById(Long id) throws SQLException{
        PreparedStatement stm = connection.prepareStatement("delete from client where id = ?");
        stm.setLong(1, id);
        stm.executeUpdate();
    }

    @Override
    public void updateClient(Client cl) throws SQLException{
        PreparedStatement stm = connection.prepareStatement("update client set firstName = ?, lastName = ?, age = ? where id = ?");
        stm.setString(1, cl.getFirstName());
        stm.setString(2, cl.getLastName());
        stm.setInt(3, cl.getAge());
        stm.setLong(4, cl.getId());
        stm.executeUpdate();
    }
}
