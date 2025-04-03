package com.prueba.TarjetasRegalo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendCreationNotification(String toEmail, String giftCardCode, java.math.BigDecimal amount) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Nueva Tarjeta Regalo Creada");
        message.setText(String.format("Se ha creado una nueva tarjeta regalo con el código: %s, monto: %s.", giftCardCode, amount));

        mailSender.send(message);
        System.out.println("Notificación de creación enviada a: " + toEmail);
    }

    public void sendRedemptionNotification(String toEmail, String giftCardCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Tarjeta Regalo Redimida");
        message.setText(String.format("La tarjeta regalo con el código: %s ha sido redimida.", giftCardCode));

        mailSender.send(message);
        System.out.println("Notificación de redención enviada a: " + toEmail);
    }
}
