package be.vdab.testexpo.tickets;

import be.vdab.testexpo.bestellingen.Bestelling;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TicketRepository {
    private final JdbcClient jdbcClient;

    public TicketRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Ticket getBeschikbaarTickets() {
        var sql = """
                select juniorDag, seniorDag
                from tickets
                """;
        return jdbcClient.sql(sql)
                .query(Ticket.class)
                .single();
    }

    public void decreaseJuniorDagTickets(int juniorDagTickets) {
        var sql = """
                update tickets
                 set juniorDag = juniordag - ?
                """;
        jdbcClient.sql(sql)
                .param(juniorDagTickets)
                .update();
    }

    public void decreaseSeniorDagTickets(int seniorDagTickets) {
        var sql = """
                update tickets
                 set seniorDag = seniordag - ?
                """;
        jdbcClient.sql(sql)
                .param(seniorDagTickets)
                .update();
    }

    public void decreaseJuniorDagEnSeniorDagTickets(int juniorTickets, int seniorTickets) {
        var sql = """
                update tickets
                 set juniorDag = juniordag - ?,
                 seniorDag = seniordag - ?
                """;
        jdbcClient.sql(sql)
                .params(juniorTickets, seniorTickets)
                .update();
    }
}
