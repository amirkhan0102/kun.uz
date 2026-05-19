package dastrulash.uz.kun.uz.service;

import dastrulash.uz.kun.uz.entity.EmailHistoryEntity;
import dastrulash.uz.kun.uz.exceptions.AppBadException;
import dastrulash.uz.kun.uz.repository.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
        // Limit tekshirish
        checkEmailLimit(toEmail);

        String message = "Tasdiqlash kodingiz: " + code + "\n\nKod 2 daqiqa ichida amal qiladi.";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(fromEmail);
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("Tasdiqlash kodi - TopNews");
        mailMessage.setText(message);
        mailSender.send(mailMessage);

        // Tarixga saqlash
        saveHistory(toEmail, message);
    }

}