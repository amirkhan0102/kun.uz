package dastrulash.uz.kun.uz.service;

import dastrulash.uz.kun.uz.entity.EmailHistoryEntity;
import dastrulash.uz.kun.uz.exceptions.AppBadException;
import dastrulash.uz.kun.uz.repository.EmailHistoryRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendVerificationEmail(String toEmail, String verificationLink) {
        // 1 daqiqada 4 tadan ko'p email yuborilmasin
        checkEmailLimit(toEmail);

        String message = "Emailingizni tasdiqlash uchun quyidagi linkga bosing:\n" + verificationLink;

        // Email yuborish
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(fromEmail);
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("Email Verification - TopNews");
        mailMessage.setText(message);
        mailSender.send(mailMessage);

        // Tarixga saqlash
        saveHistory(toEmail, message);
    }

    private void checkEmailLimit(String email) {
        LocalDateTime from = LocalDateTime.now().minusMinutes(1);
        LocalDateTime to = LocalDateTime.now();
        Integer count = emailHistoryRepository.countByEmailAndCreatedDateBetween(email, from, to);
        if (count >= 4) {
            throw new AppBadException("Email limit exceeded. Try again later.");
        }
    }

    private void saveHistory(String email, String message) {
        EmailHistoryEntity entity = new EmailHistoryEntity();
        entity.setEmail(email);
        entity.setMessage(message);
        emailHistoryRepository.save(entity);
    }
    public void sendVerificationCode(String toEmail, String code) {
        checkEmailLimit(toEmail);

        String htmlMessage = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "  <style>" +
                "    body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }" +
                "    .container { max-width: 600px; margin: 40px auto; background-color: #ffffff; border-radius: 10px; overflow: hidden; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }" +
                "    .header { background-color: #e63946; padding: 30px; text-align: center; }" +
                "    .header h1 { color: #ffffff; margin: 0; font-size: 28px; letter-spacing: 2px; }" +
                "    .body { padding: 40px 30px; text-align: center; }" +
                "    .body p { color: #555555; font-size: 16px; line-height: 1.6; }" +
                "    .code { display: inline-block; background-color: #e63946; color: #ffffff; font-size: 36px; font-weight: bold; letter-spacing: 10px; padding: 15px 30px; border-radius: 8px; margin: 20px 0; }" +
                "    .footer { background-color: #f4f4f4; padding: 20px; text-align: center; }" +
                "    .footer p { color: #aaaaaa; font-size: 12px; margin: 0; }" +
                "  </style>" +
                "</head>" +
                "<body>" +
                "  <div class='container'>" +
                "    <div class='header'>" +
                "      <h1>📰 TopNews</h1>" +
                "    </div>" +
                "    <div class='body'>" +
                "      <p>Salom! <strong>" + toEmail + "</strong></p>" +
                "      <p>Hisobingizni tasdiqlash uchun quyidagi kodni kiriting:</p>" +
                "      <div class='code'>" + code + "</div>" +
                "      <p>⏰ Kod <strong>2 daqiqa</strong> ichida amal qiladi.</p>" +
                "      <p>Agar siz ro'yxatdan o'tmagan bo'lsangiz, bu xabarni e'tiborsiz qoldiring.</p>" +
                "    </div>" +
                "    <div class='footer'>" +
                "      <p>© 2026 TopNews. Barcha huquqlar himoyalangan.</p>" +
                "    </div>" +
                "  </div>" +
                "</body>" +
                "</html>";

        // HTML email yuborish
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Tasdiqlash kodi - TopNews");
            helper.setText(htmlMessage, true); // true = HTML
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new AppBadException("Email yuborishda xatolik: " + e.getMessage());
        }

        saveHistory(toEmail, htmlMessage);
    }

}