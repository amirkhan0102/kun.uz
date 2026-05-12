package dastrulash.uz.kun.uz.repository;

import dastrulash.uz.kun.uz.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository  extends JpaRepository<RegionEntity, Long> {


    List<RegionEntity> findAllByVisibleTrueOrderByCreatedDateDesc();
    Boolean existsByKey(String key);




}
