package com.broker.chain;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class StepBEmailNotification extends BaseHandler {
    private final JavaMailSender mailSender;
    public StepBEmailNotification(JavaMailSender mailSender) { this.mailSender = mailSender; }
    @Override
    public void handle(ChainState state) {
        try {
            System.out.println("[PASO B] Enviando Notificación por Email...");
            state.setEmailResponse("Email enviado con éxito");
            next(state);
        } catch (Exception e) {
            System.err.println("Error en correo: " + e.getMessage());
            next(state);
        }
    }
}
