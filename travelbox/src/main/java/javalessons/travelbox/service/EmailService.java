package javalessons.travelbox.service;

import jakarta.mail.internet.MimeMessage;
import javalessons.travelbox.model.Trip;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendEmailWithAttachment(Trip trip, String filePath) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();

        FileSystemResource file = new FileSystemResource(new File(filePath));
        mimeMessageHelper.addAttachment(file.getFilename(), file);

        context.setVariable("name", trip.getOwnerName());
        context.setVariable("title", trip.getTitle());
        context.setVariable("city", trip.getCity());
        context.setVariable("startDate", trip.getStartDate());
        context.setVariable("endDate", trip.getEndDate());
        context.setVariable("fileName", file.getFilename());

        String htmlContent = templateEngine.process("email-template", context);
        mimeMessageHelper.setTo(trip.getEmail());
        mimeMessageHelper.setSubject("Ваш маршрут: " + trip.getTitle());
        mimeMessageHelper.setFrom("alex.minonis@gmail.com");
        mimeMessageHelper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);
    }
}
