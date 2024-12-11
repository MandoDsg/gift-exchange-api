package com.mandodsg.gift_exchange_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);  // Destinatario
        message.setSubject(subject);  // Asunto
        message.setText(text);  // Cuerpo del correo
        mailSender.send(message);  // Enviar correo
    }
}
