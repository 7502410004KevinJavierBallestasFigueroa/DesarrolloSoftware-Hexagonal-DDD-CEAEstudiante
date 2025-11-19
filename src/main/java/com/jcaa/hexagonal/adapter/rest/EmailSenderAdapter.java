package com.jcaa.hexagonal.adapter.rest;

import com.jcaa.hexagonal.core.port.EmailSenderPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderAdapter implements EmailSenderPort {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailSenderAdapter.class);
    
    @Override
    public void sendEmail(String to, String subject, String body) {
        // Implementación de consola (stub)
        // En producción, aquí se integraría con un servicio de email (SMTP, SendGrid, etc.)
        if (!logger.isInfoEnabled()) {
            return;
        }
        String formatted = String.format(
                "=== EMAIL ===%nTo: %s%nSubject: %s%nBody:%n%s%n=============",
                to,
                subject,
                body
        );
        logger.info(formatted);
    }
}
