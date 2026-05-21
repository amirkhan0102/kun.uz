package dastrulash.uz.kun.uz.repository;

import dastrulash.uz.kun.uz.entity.EmailHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EmailHistoryRepository extends JpaRepository<EmailHistoryEntity, Integer> {

    List<EmailHistoryEntity> findByEmail(String email);

    // 1 daqiqa ichida nechta email yuborilgan
    Integer countByEmailAndCreatedDateBetween(String email,
                                              java.time.LocalDateTime from,
                                              java.time.LocalDateTime to);


    List<EmailHistoryEntity> findByCreatedDateBetween(LocalDateTime from, LocalDateTime to);
}