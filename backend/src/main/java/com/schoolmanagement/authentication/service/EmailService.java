package com.schoolmanagement.authentication.service;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void envoyerLienReinitialisation(
            String email,
            String token) {

        String lien =
                "http://localhost:5173/reinitialiser-mot-de-passe?token="
                        + token;

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Réinitialisation du mot de passe");
        message.setText(
                "Bonjour,\n\n"
                        + "réinitialiser votre mot de passe en .\n\n"
                        + "Cliquant sur le lien suivant :\n"
                        + lien
                        + "\n\n"
                        + "Ce lien est valable pendant 15 minutes."
        );

        mailSender.send(message);
    }
}