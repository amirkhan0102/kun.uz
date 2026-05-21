package dastrulash.uz.kun.uz.service;

import dastrulash.uz.kun.uz.entity.EmailHistoryEntity;
import dastrulash.uz.kun.uz.repository.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service

public class EmailHistoryService {


    @Autowired
    private EmailHistoryRepository emailHistoryRepository;


    //1 get by EMAIL
    public List<EmailHistoryEntity> getByEmail(String email) {
        return emailHistoryRepository.findByEmail(email);
    }

    // 2 get by date
    public List<EmailHistoryEntity> getByDate(LocalDateTime from, LocalDateTime to) {
        return emailHistoryRepository.findByCreatedDateBetween(from, to);
    }

    // 3. Pagination (ADMIN)

    public Page<EmailHistoryEntity> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        return emailHistoryRepository.findAll(pageable);
    }


}
