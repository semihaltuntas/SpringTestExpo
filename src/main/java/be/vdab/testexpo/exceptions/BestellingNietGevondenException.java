package be.vdab.testexpo.exceptions;

public class BestellingNietGevondenException extends RuntimeException {
    private final long id;

    public BestellingNietGevondenException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
