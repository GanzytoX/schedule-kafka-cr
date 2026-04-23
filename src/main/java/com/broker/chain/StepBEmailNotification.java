package com.broker.chain;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StepBEmailNotification extends BaseHandler {

    private final JavaMailSender mailSender;

    @Override
    public void handle(ChainState state) {
        try {
            System.out.println("[PASO B] Enviando Notificación por Email Real a: gonzalo.gb11.ggb@gmail.com");

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("gonzalo.gb11.ggb@gmail.com");
            message.setSubject("Broker Alert: Job Processed - " + state.getJobId());
            message.setText("El trabajo de tipo " + state.getJobType() + " con ID " + state.getJobId() + 
                            " ha pasado por la cadena de responsabilidad con éxito.\n\nDatos: " + state.getData());

            mailSender.send(message);
            state.setEmailResponse("Email enviado con éxito");
            next(state);
        } catch (Exception e) {
            System.err.println("Error enviando correo: " + e.getMessage());
            state.setEmailResponse("Error: " + e.getMessage());
            next(state);
        }
    }
}
