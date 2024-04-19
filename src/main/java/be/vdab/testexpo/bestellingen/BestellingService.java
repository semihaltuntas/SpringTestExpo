package be.vdab.testexpo.bestellingen;

import be.vdab.testexpo.exceptions.OnvoldoendeTicketsBeschikbaar;
import be.vdab.testexpo.tickets.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Scanner;

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

        Scanner scanner = new Scanner(System.in);
        System.out.println("Hoeveel juniordagtickets wil je verminderen ?");
        var ticketAantal = scanner.nextInt();

        switch (bestelling.getTicketType()) {

            case 1 -> {
                if (juniorDagTickets <= 0) {
                    throw new OnvoldoendeTicketsBeschikbaar("Onvoldoende Junior Dag tickets!");
                }
                ticketRepository.decreaseJuniorDagTickets(ticketAantal);
            }
            case 2 -> {
                if (seniorDagTickets <= 0) {
                    throw new OnvoldoendeTicketsBeschikbaar("Onvoldoende Senior Dag tickets!");
                }
                ticketRepository.decreaseSeniorDagTickets(ticketAantal);
            }
            case 3 -> {
                if (juniorDagTickets <= 0 || seniorDagTickets <= 0) {
                    throw new OnvoldoendeTicketsBeschikbaar("Onvoldoende Junior en Senior Dag tickets!");
                }
                ticketRepository.decreaseJuniorDagEnSeniorDagTickets(ticketAantal, ticketAantal);
            }
        }
        bestellingRepository.createBestelling(bestelling);
    }
}
