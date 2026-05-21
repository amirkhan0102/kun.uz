package dastrulash.uz.kun.uz.service;

import dastrulash.uz.kun.uz.entity.SmsHistoryEntity;
import dastrulash.uz.kun.uz.repository.SmsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SmsHistoryService {

    @Autowired
    private SmsHistoryRepository smsHistoryRepository;

    // 1. Get by phone
    public List<SmsHistoryEntity> getByPhone(String phone) {
        return smsHistoryRepository.findByPhone(phone);
    }

    // 2. Get by date
    public List<SmsHistoryEntity> getByDate(LocalDateTime from, LocalDateTime to) {
        return smsHistoryRepository.findByCreatedDateBetween(from, to);
    }

    // 3. Pagination
    public Page<SmsHistoryEntity> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        return smsHistoryRepository.findAll(pageable);
    }
}