package org.fasttrackit.service;

import org.fasttrackit.domain.Agenda;
import org.fasttrackit.persistance.AgendaRepository;
import org.fasttrackit.transfer.CreateAgendaEntryRequest;
import org.fasttrackit.transfer.UpdateAgendaEntryRequest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AgendaService {

    private AgendaRepository agendaRepository = new AgendaRepository();

    public void createEntry(CreateAgendaEntryRequest request ) throws SQLException, ClassNotFoundException {
        System.out.println("Creating agenda entry: " + request);
        agendaRepository.createEntry(request);
    }

    public void updateEntry(int id, UpdateAgendaEntryRequest request) throws SQLException, ClassNotFoundException {
        System.out.println("Updating agenda entry: " + request);
        agendaRepository.updateEntry(id, request);
    }

    public void deleteEntry(int id) throws SQLException, ClassNotFoundException {
        System.out.println("Deleting task: " + id);
        agendaRepository.deleteEntry(id);
    }

    public List<Agenda> getEntries() throws IOException, SQLException, ClassNotFoundException {
        System.out.println("Searching for all agenda entries.");
        return agendaRepository.getContacts();
    }

    public List<Agenda> getContactByName(CreateAgendaEntryRequest request)
            throws IOException, SQLException, ClassNotFoundException {
        System.out.println("Searching for the requested entry.");
        return agendaRepository.getContactByName(request);
    }
}
