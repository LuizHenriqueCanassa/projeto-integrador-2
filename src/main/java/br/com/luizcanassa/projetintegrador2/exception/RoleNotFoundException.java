package br.com.luizcanassa.projetintegrador2.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(final String message) {
        super(message);
    }
}
