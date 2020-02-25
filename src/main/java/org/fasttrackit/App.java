package org.fasttrackit;

import org.fasttrackit.domain.Agenda;
import org.fasttrackit.persistance.AgendaRepository;
import org.fasttrackit.transfer.CreateAgendaEntryRequest;
import org.fasttrackit.transfer.UpdateAgendaEntryRequest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main( String[] args ) throws SQLException, IOException, ClassNotFoundException {
        // here we have this object in order to be able to access functions/methods inside it
        AgendaRepository agendaRepository= new AgendaRepository();

// this creates a new task
        CreateAgendaEntryRequest request = new CreateAgendaEntryRequest();
        request.setFirst_name("Simina");
        request.setLast_name("Crecan");
        request.setPhone_number("0755125532");
        agendaRepository.createEntry(request);

// this updates a task
//        UpdateAgendaEntryRequest request = new UpdateAgendaEntryRequest();
//        request.setLast_name("Sava");
//        agendaRepository.updateEntry(1, request);

// this deletes a task
//        agendaRepository.deleteEntry(1);

        List<Agenda> contacts = agendaRepository.getContacts();
        System.out.println(contacts);

    }
}
