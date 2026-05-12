package dastrulash.uz.kun.uz.repository;

import dastrulash.uz.kun.uz.entity.SectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionRepository extends JpaRepository<SectionEntity,Long> {


    List<SectionEntity> findAllByVisibleTrueOrderByOrderNumberAsc();
    Boolean existsByKey(String key);



}
