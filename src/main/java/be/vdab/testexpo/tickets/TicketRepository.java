package be.vdab.testexpo.tickets;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

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

    public void decreaseEenJuniorDagTickets() {
        var sql = """
                update tickets
                 set juniorDag = juniordag - 1
                """;
        jdbcClient.sql(sql)
                .update();
    }

    public void decreaseSeniorDagTickets() {
        var sql = """
                update tickets
                 set seniorDag = seniordag - 1
                """;
        jdbcClient.sql(sql)
                .update();
    }

    public void decreaseJuniorDagEnSeniorDagTickets() {
        var sql = """
                update tickets
                 set juniorDag = juniordag - 1,
                 seniorDag = seniordag - 1
                """;
        jdbcClient.sql(sql)
                .update();
    }
}
