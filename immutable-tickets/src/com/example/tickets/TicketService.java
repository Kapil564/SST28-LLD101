package com.example.tickets;

import java.util.ArrayList;
import java.util.List;


/**
 * Service layer that creates tickets.
 *
 * CURRENT STATE (BROKEN ON PURPOSE):
 * - creates partially valid objects
 * - mutates after creation (bad for auditability)
 * - validation is scattered & incomplete
 *
 * TODO (student):
 * - After introducing immutable IncidentTicket + Builder, refactor this to stop mutating.
 */
public class TicketService {

    public IncidentTicket createTicket(String id, String reporterEmail, String title) {
        // scattered validation (incomplete on purpose)
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("id required");
        if (reporterEmail == null || !reporterEmail.contains("@")) throw new IllegalArgumentException("email invalid");
        if (title == null || title.trim().isEmpty()) throw new IllegalArgumentException("title required");

        return new IncidentTicket.Builder(id,reporterEmail,title).priority("MEDIUM").source("CLI").customerVisible(false).addTag("NEW").build();
    }

    public IncidentTicket escalateToCritical(IncidentTicket t) {
        return t.toBuilder()
                .priority("CRITICAL")
                .addTag("ESCALATED")
                .build();
    }
    public IncidentTicket assign(IncidentTicket ticket, String assigneeEmail) {

        if (assigneeEmail != null && !assigneeEmail.contains("@")) {
            throw new IllegalArgumentException("assigneeEmail invalid");
        }

        return ticket.toBuilder()
                .assigneeEmail(assigneeEmail)
                .build();
    }
}