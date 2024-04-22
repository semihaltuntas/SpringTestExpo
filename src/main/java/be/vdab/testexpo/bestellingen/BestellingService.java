package be.vdab.testexpo.bestellingen;

import be.vdab.testexpo.exceptions.OnvoldoendeTicketsBeschikbaar;
import be.vdab.testexpo.tickets.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BestellingService {
    private final BestellingRepository bestellingRepository;
    private final TicketRepository ticketRepository;

    public BestellingService(BestellingRepository bestellingRepository, TicketRepository ticketRepository) {
        this.bestellingRepository = bestellingRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public void create(Bestelling bestelling) {
        var juniorDagTickets = ticketRepository.getBeschikbaarTickets().getJuniorDag();
        var seniorDagTickets = ticketRepository.getBeschikbaarTickets().getSeniorDag();

        switch (bestelling.getTicketType()) {

            case 1 -> {
                if (juniorDagTickets <= 0) {
                    throw new OnvoldoendeTicketsBeschikbaar("Onvoldoende Junior Dag tickets!");
                }
                ticketRepository.decreaseEenJuniorDagTickets();
                System.out.println("Je hebt een ticket geboekt voor juniorDag.");
            }
            case 2 -> {
                if (seniorDagTickets <= 0) {
                    throw new OnvoldoendeTicketsBeschikbaar("Onvoldoende Senior Dag tickets!");
                }
                ticketRepository.decreaseSeniorDagTickets();
            }
            case 3 -> {
                if (juniorDagTickets <= 0 || seniorDagTickets <= 0) {
                    throw new OnvoldoendeTicketsBeschikbaar("Onvoldoende Junior en Senior Dag tickets!");
                }
                ticketRepository.decreaseJuniorDagEnSeniorDagTickets();
            }
        }
        bestellingRepository.createBestelling(bestelling);
    }
}
