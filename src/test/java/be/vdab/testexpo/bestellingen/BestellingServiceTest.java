package be.vdab.testexpo.bestellingen;

import be.vdab.testexpo.exceptions.OnvoldoendeTicketsBeschikbaar;
import be.vdab.testexpo.tickets.Ticket;
import be.vdab.testexpo.tickets.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@JdbcTest
@Import({BestellingService.class, TicketRepository.class, BestellingRepository.class})
@Sql("/bestellingen.sql")
@ExtendWith(MockitoExtension.class)
public class BestellingServiceTest {
    @Mock
    TicketRepository ticketRepository;

    private final BestellingRepository bestellingRepository;
    private BestellingService bestellingService;
    private final JdbcClient jdbcClient;
    private static final String BESTELLINGEN_TABLE = "bestellingen";
    private Bestelling validJuniorBestelling;
    private Bestelling validSeniorBestelling;
    private Bestelling validAllInBestelling;


    public BestellingServiceTest(BestellingService bestellingService, JdbcClient jdbcClient, BestellingRepository bestellingRepository) {
        this.bestellingService = bestellingService;
        this.jdbcClient = jdbcClient;
        this.bestellingRepository = bestellingRepository;
    }

    @BeforeEach
    void beforeEach() {
        bestellingService = new BestellingService(bestellingRepository, ticketRepository);
    }

    @Test
    void createValidJuniorDagTicketMetTicketType1() {
        when(ticketRepository.getBeschikbaarTickets()).thenReturn(new Ticket(5, 10));
        int aantalRecordsVoorCreate = JdbcTestUtils.countRowsInTable(jdbcClient, BESTELLINGEN_TABLE);

        validJuniorBestelling = new Bestelling(1, "Frank", 1);

        assertEquals(1, validJuniorBestelling.getTicketType());

        assertThatCode(() -> bestellingService.create(validJuniorBestelling)).doesNotThrowAnyException();

        var aantalRecordsNaCreate = JdbcTestUtils.countRowsInTable(jdbcClient, BESTELLINGEN_TABLE);

        assertThat(aantalRecordsNaCreate).isEqualTo(aantalRecordsVoorCreate +1);
    }

    @Test
    void createValidSeniorDagTicketMetTicketType2() {
        when(ticketRepository.getBeschikbaarTickets()).thenReturn(new Ticket(5, 10));
        var aantalRecordsVoorCreate = JdbcTestUtils.countRowsInTable(jdbcClient, BESTELLINGEN_TABLE);

        validSeniorBestelling = new Bestelling(1, "Marco", 2);
        assertEquals(2, validSeniorBestelling.getTicketType());

        assertThatCode(() -> bestellingService.create(validSeniorBestelling)).doesNotThrowAnyException();

        var aantalRecordsNaCreate = JdbcTestUtils.countRowsInTable(jdbcClient, BESTELLINGEN_TABLE);
        assertThat(aantalRecordsNaCreate).isEqualTo(aantalRecordsVoorCreate +1);
    }

    @Test
    void createValidJuniorEnSeniorDagTicketsMetTicketType3() {
        when(ticketRepository.getBeschikbaarTickets()).thenReturn(new Ticket(5, 10));
        var aantalRecordsVoorCreate = JdbcTestUtils.countRowsInTable(jdbcClient, BESTELLINGEN_TABLE);

        validAllInBestelling = new Bestelling(1, "Alex", 3);
        assertEquals(3, validAllInBestelling.getTicketType());

        assertThatCode(() -> bestellingService.create(validAllInBestelling)).doesNotThrowAnyException();

        var aantalRecordsNaCreate = JdbcTestUtils.countRowsInTable(jdbcClient, BESTELLINGEN_TABLE);
        assertThat(aantalRecordsNaCreate).isEqualTo(aantalRecordsVoorCreate +1);
    }

    @Test
    void createInvalidJuniorDagTicket(){
        when(ticketRepository.getBeschikbaarTickets()).thenReturn(new Ticket(0,10));

        validJuniorBestelling = new Bestelling(1, "Frank", 1);

        assertThatThrownBy( ()-> bestellingService.create(validJuniorBestelling))
                .isInstanceOf(OnvoldoendeTicketsBeschikbaar.class)
                .hasMessage("Onvoldoende Junior Dag tickets!");
    }
    @Test
    void createInvalidSeniorDagTicket(){
        when(ticketRepository.getBeschikbaarTickets()).thenReturn(new Ticket(5,0));

        validSeniorBestelling = new Bestelling(1, "Marco", 2);

        assertThatThrownBy( ()-> bestellingService.create(validSeniorBestelling))
                .isInstanceOf(OnvoldoendeTicketsBeschikbaar.class)
                .hasMessage("Onvoldoende Senior Dag tickets!");
    }
    @Test
    void createInvalidJuniorEnSeniorDagTickets(){
        when(ticketRepository.getBeschikbaarTickets()).thenReturn(new Ticket(0,0));

        validAllInBestelling = new Bestelling(1, "Alex", 3);

        assertThatThrownBy( ()-> bestellingService.create(validAllInBestelling))
                .isInstanceOf(OnvoldoendeTicketsBeschikbaar.class)
                .hasMessage("Onvoldoende Junior en Senior Dag tickets!");
    }


}