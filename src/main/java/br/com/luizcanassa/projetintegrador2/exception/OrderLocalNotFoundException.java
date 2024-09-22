package br.com.luizcanassa.projetintegrador2.exception;

public class OrderLocalNotFoundException extends RuntimeException {
    public OrderLocalNotFoundException(String message) {
        super(message);
    }
}
