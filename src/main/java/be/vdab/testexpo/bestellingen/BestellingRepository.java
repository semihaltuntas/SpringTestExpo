package be.vdab.testexpo.bestellingen;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class BestellingRepository {
    private final JdbcClient jdbcClient;

    public BestellingRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public void createBestelling(Bestelling bestelling) {
        var sql = """
                insert into bestellingen(naam,ticketType)
                values (?, ?)
                """;
        jdbcClient.sql(sql)
                .params(bestelling.getNaam(), bestelling.getTicketType())
                .update();
    }
}
