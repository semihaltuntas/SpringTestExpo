package be.vdab.testexpo.bestellingen;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class BestellingTest {

    @Test
    void eenBestellingDieLukt() {
        new Bestelling(0, "Semih", 3);
    }
    @Test
    void deNaamIsVerplicht(){
        assertThatIllegalArgumentException().isThrownBy(
                ()->new Bestelling(0,"",2)
        );
    }
    @Test
    void ongeldigTicketTypeNummersGooitException(){
       assertThatIllegalArgumentException().isThrownBy(
               ()-> new Bestelling(0,"Semih",0));
       assertThatIllegalArgumentException().isThrownBy(
               ()-> new Bestelling(0,"Semih",4));
        assertThatIllegalArgumentException().isThrownBy(
                ()-> new Bestelling(0,"Semih",-1));
    }

}