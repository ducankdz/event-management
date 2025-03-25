package com.ptit.event.services.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{
    private final JavaMailSender mailSender;

    public String generateOtp(){
        Random random = new Random();
        int otpNum = random.nextInt(9000) + 1000;
        return String.valueOf(otpNum);
    }

    @Override
    public void sendOtpMail(String to, String otp) throws MessagingException {
        String subject = "Xác thực tài khoản";
        String text = "Mã OTP của bạn là " + otp + ". Mã này sẽ hết hạn sau 2 phút.";
        sendEmail(to, subject,text);
    }

    @Override
    public void sendEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,"utf-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
        try {
            mailSender.send(message);
        }
        catch (MailSendException e){
            throw new MailSendException("Fail to send email to " + to);
        }
    }
}
