package br.com.netdeal.backend.services.exception;

public class DataIntegrityException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    // Repassando a mensagem de exceção
    public DataIntegrityException(String msg) {
        super(msg);
    }

    // Recebe a mensagem e a causa do que aconteceu antes
    public DataIntegrityException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
