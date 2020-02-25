package org.fasttrackit.persistance;

import org.fasttrackit.domain.Agenda;
import org.fasttrackit.transfer.CreateAgendaEntryRequest;
import org.fasttrackit.transfer.UpdateAgendaEntryRequest;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendaRepository {

    public void createEntry(CreateAgendaEntryRequest request) throws SQLException, ClassNotFoundException {
        // preventing sql injection by avoiding concatenation and using prepared statement
        String sql = "INSERT INTO contact (first_name, last_name, phone_number) VALUES (?,?,?)";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            //here, we declare which parameters from (?,?) should be replaced with what data.
            preparedStatement.setString(1, request.getFirst_name());
            preparedStatement.setString(2, request.getLast_name());
            preparedStatement.setString(3, request.getPhone_number());

            preparedStatement.executeUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateEntry(int id, UpdateAgendaEntryRequest request) throws SQLException, ClassNotFoundException {
        // preventing sql injection by avoiding concatenation and using prepared statement
        String sql = "UPDATE contact SET first_name = ?, last_name = ?, phone_number = ? WHERE id = ?";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            //here, we declare which parameters from (?,?) should be replaced with what data.
            preparedStatement.setString(1, request.getFirst_name());
            preparedStatement.setString(2, request.getLast_name());
            preparedStatement.setString(3, request.getPhone_number());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteEntry(int id) throws SQLException, ClassNotFoundException {
        // preventing sql injection by avoiding concatenation and using prepared statement
        String sql = "DELETE FROM contact WHERE id = ?";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            //here, we declare which parameters from (?,?) should be replaced with what data.
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Agenda> getContactByName(CreateAgendaEntryRequest request) throws IOException, SQLException, ClassNotFoundException {
        String sql = "SELECT first_name, last_name, phone_number FROM contact WHERE first_name LIKE '?%'";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, request.getFirst_name());
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Agenda> singleContact = new ArrayList<>();

            while (resultSet.next()) {
                Agenda agenda = new Agenda();
                agenda.setFirst_name(resultSet.getString("first_name"));
                agenda.setLast_name(resultSet.getString("last_name"));
                agenda.setPhone_number(resultSet.getString("phone_number"));

                singleContact.add(agenda);
            }
            return singleContact;
        }
    }

    public List<Agenda> getContacts() throws IOException, SQLException, ClassNotFoundException {
        // statement should be used only for no parameter queries.
        String sql = "SELECT id, first_name, last_name, phone_number FROM contact";
        try (Connection connection = DatabaseConfiguration.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            List<Agenda> contacts = new ArrayList<>();

            while (resultSet.next()) {
                Agenda agenda = new Agenda();
                agenda.setId(resultSet.getInt("id"));
                agenda.setFirst_name(resultSet.getString("first_name"));
                agenda.setLast_name(resultSet.getString("last_name"));
                agenda.setPhone_number(resultSet.getString("phone_number"));

                contacts.add(agenda);
            }
            return contacts;
        }
    }
}
