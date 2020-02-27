package org.fasttrackit.web;

import org.fasttrackit.config.ObjectMapperConfiguration;
import org.fasttrackit.domain.Agenda;
import org.fasttrackit.persistance.AgendaRepository;
import org.fasttrackit.service.AgendaService;
import org.fasttrackit.transfer.CreateAgendaEntryRequest;
import org.fasttrackit.transfer.UpdateAgendaEntryRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/contacts")
public class AgendaServlet extends HttpServlet {

    private AgendaService agendaService = new AgendaService();

    //endpoint
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setAccessControlHeaders(resp);

        CreateAgendaEntryRequest request = ObjectMapperConfiguration.objectMapper.readValue(
                req.getReader(), CreateAgendaEntryRequest.class);
        try {
            agendaService.createEntry(request);
        } catch (SQLException | ClassNotFoundException e) {
            resp.sendError(500, "Internal server error: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setAccessControlHeaders(resp);

        String id = req.getParameter("id");

        try {
            agendaService.deleteEntry(Integer.parseInt(id));
        } catch (SQLException | ClassNotFoundException e) {
            resp.sendError(500, "Internal server error: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setAccessControlHeaders(resp);

        String id = req.getParameter("id");

        UpdateAgendaEntryRequest request = ObjectMapperConfiguration.objectMapper.readValue(
                req.getReader(), UpdateAgendaEntryRequest.class);

        try {
            agendaService.updateEntry(Integer.parseInt(id), request);
        } catch (SQLException | ClassNotFoundException e) {
            resp.sendError(500, "Internal server error: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setAccessControlHeaders(resp);

        String first_name = req.getParameter("first_name");

        if (first_name != null) {
            try {
                CreateAgendaEntryRequest request = new CreateAgendaEntryRequest();
                request.setFirst_name(first_name);
                ObjectMapperConfiguration.objectMapper.writeValue(resp.getWriter(),
                        agendaService.getContactByName(request));
            } catch (SQLException | ClassNotFoundException e) {
                resp.sendError(500, "Internal server error: " + e.getMessage());
            }

        } else {

            try {
                List<Agenda> list = agendaService.getEntries();

                String response = ObjectMapperConfiguration.objectMapper.writeValueAsString(list);

                resp.getWriter().print(response);

            } catch (SQLException | ClassNotFoundException e) {
                resp.sendError(500, "Internal server error: " + e.getMessage());
            }
        }
    }

    // for pre-flight requests
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setAccessControlHeaders(resp);
    }

    // CORS configuration (CROSS-ORIGIN-RESOURCE-SHARING)
    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "content-type");
    }


}
