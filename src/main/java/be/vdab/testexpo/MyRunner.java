package be.vdab.testexpo;

import be.vdab.testexpo.bestellingen.Bestelling;
import be.vdab.testexpo.bestellingen.BestellingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements CommandLineRunner {
    private final BestellingService bestellingService;

    public MyRunner(BestellingService bestellingService) {
        this.bestellingService = bestellingService;
    }

    @Override
    public void run(String... args) {
//        bestellingService.create(new Bestelling(0,"Ahmet",1));
//        System.out.println("de Bestelling is gecreeerd! ");
    }
}
