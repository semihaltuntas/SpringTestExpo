package be.vdab.testexpo.bestellingen;

public class Bestelling {
    private long id;
    private String naam;
    private int ticketType;

    public Bestelling(long id, String naam, int ticketType) {
        if (naam.isEmpty()) {
            throw new IllegalArgumentException("Naam mag niet leeg zijn.!");
        }
        if (ticketType < 1 || ticketType > 3) {
            throw new IllegalArgumentException("TicketType moet 1,2 of 3 zijn!");
        }
        this.id = id;
        this.naam = naam;
        this.ticketType = ticketType;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public int getTicketType() {
        return ticketType;
    }
}
