package com.italy.agrifood.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  @Autowired
  private JavaMailSender mailSender;

  public void sendPasswordResetEmail(String to, String resetLink) {
    try {
      MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage());
      messageHelper.setTo(to);
      messageHelper.setSubject("Password Reset Request");
      messageHelper.setText("To reset your password, click the following link: " + resetLink);

      mailSender.send(messageHelper.getMimeMessage());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
