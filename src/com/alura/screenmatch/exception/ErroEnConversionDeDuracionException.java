package com.alura.screenmatch.exception;

public class ErroEnConversionDeDuracionException extends RuntimeException {
    private String mensaje;

    public ErroEnConversionDeDuracionException(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String getMessage() {
        return this.mensaje;
    }
}
