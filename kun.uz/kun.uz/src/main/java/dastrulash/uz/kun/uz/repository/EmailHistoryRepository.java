package dastrulash.uz.kun.uz.repository;

import dastrulash.uz.kun.uz.entity.EmailHistoryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity, Integer> {

    List<EmailHistoryEntity> findByEmail(String email);

    // 1 daqiqa ichida nechta email yuborilgan
    Integer countByEmailAndCreatedDateBetween(String email,
                                              java.time.LocalDateTime from,
                                              java.time.LocalDateTime to);
}