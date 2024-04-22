package be.vdab.testexpo;

import be.vdab.testexpo.bestellingen.Bestelling;
import be.vdab.testexpo.bestellingen.BestellingService;
import be.vdab.testexpo.exceptions.OnvoldoendeTicketsBeschikbaar;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MyRunner implements CommandLineRunner {
    private final BestellingService bestellingService;

    public MyRunner(BestellingService bestellingService) {
        this.bestellingService = bestellingService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Voer uw naam in: ");
        var naam = scanner.nextLine();

        System.out.println("Kies het type ticket (1 voor junior, 2 voor senior, 3 voor beide dagen):");
        var ticketType = scanner.nextInt();

        try{
            bestellingService.create(new Bestelling(0,naam,ticketType));
        }catch (OnvoldoendeTicketsBeschikbaar ex){
            System.err.println(ex.getMessage());
        }

    }
}
