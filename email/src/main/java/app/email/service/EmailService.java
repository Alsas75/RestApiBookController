package app.email.service;

import jakarta.mail.internet.MimeMessage;
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

    public void sendTemplateEmail(String to, String subject, Map<String, Object> model) throws Exception{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper =
                new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);
        Context context = new Context();
        context.setVariables(model);
        String htmlContent = templateEngine.process("email-template", context);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setFrom("igor.sonstnix@gmail.com");
        mimeMessageHelper.setText(htmlContent, true);
        javaMailSender.send(mimeMessage);
    }

    public void sendEmailWithAttachment(String to, String subject, Map<String, Object> model, File pdfFile) throws Exception{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariables(model);
        String htmlContent = templateEngine.process("email-template", context);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setFrom("igor.sonstnix@gmail.com");
        mimeMessageHelper.setText(htmlContent, true);

        FileSystemResource file = new FileSystemResource(pdfFile);
        mimeMessageHelper.addAttachment("document.pdf", file);

        javaMailSender.send(mimeMessage);
    }
}
