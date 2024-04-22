package be.vdab.testexpo.tickets;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


@JdbcTest
@Import(TicketRepository.class)
class TicketRepositoryTest {

    private final TicketRepository ticketRepository;
    private final JdbcClient jdbcClient;

    private static final String TICKETS_TABLE = "tickets";

    public TicketRepositoryTest(TicketRepository ticketRepository, JdbcClient jdbcClient) {
        this.ticketRepository = ticketRepository;
        this.jdbcClient = jdbcClient;
    }

    @Test
    void isTableEenRij() {
        var aantalRecordsRij = JdbcTestUtils.countRowsInTable(jdbcClient, TICKETS_TABLE);
        assertThat(aantalRecordsRij).isOne();
    }

    @Test
    void findBeschikbaarJuniorTickets() {
        TicketRepository ticketRepository = mock(TicketRepository.class);
        Ticket expectedRicket = new Ticket(100, 200);

        when(ticketRepository.getBeschikbaarTickets()).thenReturn(expectedRicket);

        var juniorDagAantal = ticketRepository.getBeschikbaarTickets().getJuniorDag();
        assertThat(juniorDagAantal).isEqualTo(100);

        verify(ticketRepository).getBeschikbaarTickets();

    }

    @Test
    void findBeschikbaarSeniorTickets() {
        TicketRepository ticketRepository = mock(TicketRepository.class);
        Ticket expectedTicket = new Ticket(100, 200);

        when(ticketRepository.getBeschikbaarTickets()).thenReturn(expectedTicket);

        var seniorDagAantal = ticketRepository.getBeschikbaarTickets().getSeniorDag();
        assertThat(seniorDagAantal).isEqualTo(200);
        verify(ticketRepository).getBeschikbaarTickets();
    }

    @Test
    void decreaseJuniorDagTickets() {
        var juniorDagAantal = ticketRepository.getBeschikbaarTickets().getJuniorDag();
        ticketRepository.decreaseEenJuniorDagTickets();
        var juniorDagAantalNaOproep = ticketRepository.getBeschikbaarTickets().getJuniorDag();
        assertThat(juniorDagAantal - 1).isEqualTo(juniorDagAantalNaOproep);
    }

    @Test
    void decreaseSeniorDagTickets() {
        var seniorDagAantal = ticketRepository.getBeschikbaarTickets().getSeniorDag();
        ticketRepository.decreaseSeniorDagTickets();
        var juniorDagAantalNaOproep = ticketRepository.getBeschikbaarTickets().getSeniorDag();
        assertThat(seniorDagAantal - 1).isEqualTo(juniorDagAantalNaOproep);
    }

    @Test
    void decreaseJuniorDagEnSeniorDagTickets() {
        int juniorDagAantal = ticketRepository.getBeschikbaarTickets().getJuniorDag();
        int seniorDagAantal = ticketRepository.getBeschikbaarTickets().getSeniorDag();
        ticketRepository.decreaseJuniorDagEnSeniorDagTickets();

        int juniorDagAantalNaOproep = ticketRepository.getBeschikbaarTickets().getJuniorDag();
        int seniorDagAantalNaOproep = ticketRepository.getBeschikbaarTickets().getSeniorDag();
        assertThat(juniorDagAantal - 1).isEqualTo(juniorDagAantalNaOproep);
        assertThat(seniorDagAantal - 1).isEqualTo(seniorDagAantalNaOproep);
    }
}