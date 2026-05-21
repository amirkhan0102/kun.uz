package dastrulash.uz.kun.uz.repository;

import dastrulash.uz.kun.uz.entity.SmsHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SmsHistoryRepository extends JpaRepository<SmsHistoryEntity, Integer> {
    List<SmsHistoryEntity> findByPhone(String phone);

    List<SmsHistoryEntity> findByCreatedDateBetween(LocalDateTime from, LocalDateTime to);

    Integer countByPhoneAndCreatedDateBetween(String phone,
                                              LocalDateTime from,
                                              LocalDateTime to);


}
