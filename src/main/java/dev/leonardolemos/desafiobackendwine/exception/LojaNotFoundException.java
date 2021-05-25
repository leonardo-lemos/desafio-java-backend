package dev.leonardolemos.desafiobackendwine.exception;

public class LojaNotFoundException extends RuntimeException {

    public LojaNotFoundException(String message) {
        super(message);
    }
}
