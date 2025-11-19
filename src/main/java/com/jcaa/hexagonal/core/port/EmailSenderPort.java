package com.jcaa.hexagonal.core.port;

public interface EmailSenderPort {
    
    void sendEmail(String to, String subject, String body);
    
    /**
     * Método de conveniencia que deja claro que el contenido es texto plano.
     * Por defecto delega en {@link #sendEmail(String, String, String)}.
     */
    default void sendPlainText(String to, String subject, String body) {
        sendEmail(to, subject, body);
    }
}

