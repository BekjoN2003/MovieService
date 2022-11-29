package com.example.test.service;

import com.example.test.config.JwtTokenFilter;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
    private final JwtTokenFilter jwtTokenFilter;


    private final MailSender mailSender;

    public MailSenderService(JwtTokenFilter jwtTokenFilter, MailSender mailSender) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.mailSender = mailSender;
    }

    public void send(String toAccount,String subject, String content){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toAccount);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        mailSender.send((simpleMailMessage));
    }
}
