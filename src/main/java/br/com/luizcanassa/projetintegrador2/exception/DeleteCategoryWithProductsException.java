package br.com.luizcanassa.projetintegrador2.exception;

public class DeleteCategoryWithProductsException extends RuntimeException {
    public DeleteCategoryWithProductsException(final String message) {
        super(message);
    }
}
