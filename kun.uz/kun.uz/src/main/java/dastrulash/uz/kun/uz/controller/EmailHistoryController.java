package dastrulash.uz.kun.uz.controller;

import dastrulash.uz.kun.uz.entity.EmailHistoryEntity;
import dastrulash.uz.kun.uz.service.EmailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/email-history")
public class EmailHistoryController {

    @Autowired
    private EmailHistoryService emailHistoryService;

    // 1. Get by email
    @GetMapping("/by-email")
    public ResponseEntity<List<EmailHistoryEntity>> getByEmail(@RequestParam String email) {
        return ResponseEntity.ok(emailHistoryService.getByEmail(email));
    }

    // 2. Get by date
    @GetMapping("/by-date")
    public ResponseEntity<List<EmailHistoryEntity>> getByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return ResponseEntity.ok(emailHistoryService.getByDate(from, to));
    }

    // 3. Pagination (ADMIN)
    @GetMapping("/pagination")
    public ResponseEntity<Page<EmailHistoryEntity>> pagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(emailHistoryService.pagination(page, size));
    }
}