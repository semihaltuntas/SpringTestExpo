package be.vdab.testexpo.bestellingen;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(BestellingRepository.class)
@Sql("/bestellingen.sql")

public class BestellingRepositoryTest {
    private final BestellingRepository bestellingRepository;
    private final JdbcClient jdbcClient;
    private static final String BESTELLINGEN_TABLE = "bestellingen";

    public BestellingRepositoryTest(BestellingRepository bestellingRepository, JdbcClient jdbcClient) {
        this.bestellingRepository = bestellingRepository;
        this.jdbcClient = jdbcClient;
    }
    @Test
    void createVoegtEenBestellingToe(){
        var id = bestellingRepository.createBestelling(new Bestelling(0,"frank",2));
        assertThat(id).isPositive();
        var aantalRecordsMetDeIdVanDeToegevoegdeBestelling = JdbcTestUtils.countRowsInTableWhere(
                jdbcClient, BESTELLINGEN_TABLE,"id ="+id);
        assertThat(aantalRecordsMetDeIdVanDeToegevoegdeBestelling).isOne();
    }
}