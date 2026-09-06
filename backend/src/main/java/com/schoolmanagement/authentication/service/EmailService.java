package com.schoolmanagement.authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender mailSender;

  @Value("${app.frontend-url:http://localhost:5173}")
  private String frontendUrl;

  public void envoyerLienReinitialisation(String email, String token) {

    String lien = frontendUrl + "/reinitialiser-mot-de-passe?token=" + token;

    SimpleMailMessage message = new SimpleMailMessage();

    message.setTo(email);
    message.setSubject("Réinitialisation du mot de passe");
    message.setText(
        """
        Bonjour,

        Pour reinitialiser votre mot de passe, cliquez sur le lien suivant :
        %s

        Ce lien est valable pendant 15 minutes.
        """
            .formatted(lien));

    mailSender.send(message);
  }
}
