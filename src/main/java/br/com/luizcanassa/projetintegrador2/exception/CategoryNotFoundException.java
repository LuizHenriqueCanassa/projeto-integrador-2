package br.com.luizcanassa.projetintegrador2.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(final String message) {
        super(message);
    }
}
