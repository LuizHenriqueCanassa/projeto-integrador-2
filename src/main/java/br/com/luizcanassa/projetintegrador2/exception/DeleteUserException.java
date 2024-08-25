package br.com.luizcanassa.projetintegrador2.exception;

public class DeleteUserException extends RuntimeException {
    public DeleteUserException(final String message) {
        super(message);
    }
}
