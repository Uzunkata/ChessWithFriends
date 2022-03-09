package com.example.server.service;

import com.example.server.dto.RecipientConfirmation;
import com.example.server.exception.CustomException;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    public static final String RECIPIENT = "recipient";

    final Configuration configuration;
    final JavaMailSender javaMailSender;

    public EmailService(Configuration configuration, JavaMailSender javaMailSender) {
        this.configuration = configuration;
        this.javaMailSender = javaMailSender;
    }

    public void sendConfirmationMail(RecipientConfirmation recipient, String template) throws MessagingException, TemplateException, IOException, CustomException {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setSubject(recipient.getSubject());
            helper.setTo(recipient.getEmail());
            String emailContent = getEmailContentConfirmation(recipient, template);
            helper.setText(emailContent, true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new CustomException("Email could not be sent due to a server error!" + e.getMessage());
        }
    }

    String getEmailContentConfirmation(RecipientConfirmation recipientConfirmation, String template) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put(RECIPIENT, recipientConfirmation);
        configuration.getTemplate(template).process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }

}

