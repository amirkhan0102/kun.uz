package dastrulash.uz.kun.uz.controller;

import dastrulash.uz.kun.uz.entity.SmsHistoryEntity;
import dastrulash.uz.kun.uz.service.SmsHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sms-history")
public class SmsHistoryController {

    @Autowired
    private SmsHistoryService smsHistoryService;

    // 1. Get by phone
    @GetMapping("/by-phone")
    public ResponseEntity<List<SmsHistoryEntity>> getByPhone(@RequestParam String phone) {
        return ResponseEntity.ok(smsHistoryService.getByPhone(phone));
    }

    // 2. Get by date
    @GetMapping("/by-date")
    public ResponseEntity<List<SmsHistoryEntity>> getByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return ResponseEntity.ok(smsHistoryService.getByDate(from, to));
    }

    // 3. Pagination
    @GetMapping("/pagination")
    public ResponseEntity<Page<SmsHistoryEntity>> pagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(smsHistoryService.pagination(page, size));
    }
}