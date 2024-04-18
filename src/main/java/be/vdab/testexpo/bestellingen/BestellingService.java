package be.vdab.testexpo.bestellingen;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BestellingService {
    private final BestellingRepository bestellingRepository;

    public BestellingService(BestellingRepository bestellingRepository) {
        this.bestellingRepository = bestellingRepository;
    }
    @Transactional
    public long create(Bestelling bestelling){
        return bestellingRepository.createBestelling(bestelling);
    }
}
