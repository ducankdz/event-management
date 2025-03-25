package com.ptit.event.services.email;

import jakarta.mail.MessagingException;

public interface EmailService {
    String generateOtp();
    void sendOtpMail(String to, String otp) throws MessagingException;
    void sendEmail(String to, String subject, String text) throws MessagingException;
}
