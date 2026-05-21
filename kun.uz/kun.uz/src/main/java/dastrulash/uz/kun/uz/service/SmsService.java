package dastrulash.uz.kun.uz.service;

import dastrulash.uz.kun.uz.entity.SmsHistoryEntity;
import dastrulash.uz.kun.uz.enums.SmsStatus;
import dastrulash.uz.kun.uz.enums.SmsType;
import dastrulash.uz.kun.uz.exceptions.AppBadException;
import dastrulash.uz.kun.uz.repository.SmsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SmsService {

    @Autowired
    private SmsHistoryRepository smsHistoryRepository;

    public void sendSms(String phone, String code) {
        // Limit tekshirish — 1 daqiqada 4 tadan ko'p SMS yuborilmasin
        checkSmsLimit(phone);

        String message = "Sizning tasdiqlash kodingiz: " + code + ". Kod 2 daqiqa ichida amal qiladi.";

        // KONSOLGA CHIQARAMIZ PAKA
        System.out.println("===== SMS =====");
        System.out.println("Tel: " + phone);
        System.out.println("Xabar: " + message);
        System.out.println("===============");

        // SAQLAB OLISH
        saveHistory(phone, message);
    }

    private void checkSmsLimit(String phone) {
        LocalDateTime from = LocalDateTime.now().minusMinutes(1);
        LocalDateTime to = LocalDateTime.now();
        Integer count = smsHistoryRepository.countByPhoneAndCreatedDateBetween(phone, from, to);
        if (count >= 4) {
            throw new AppBadException("SMS limit exceeded. Try again later.");
        }
    }

    private void saveHistory(String phone, String message) {
        SmsHistoryEntity entity = new SmsHistoryEntity();
        entity.setPhone(phone);
        entity.setMessage(message);
        entity.setStatus(SmsStatus.SENT);
        entity.setType(SmsType.VERIFICATION);
        smsHistoryRepository.save(entity);
    }
}