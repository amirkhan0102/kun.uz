package dastrulash.uz.kun.uz.repository;

import dastrulash.uz.kun.uz.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {



    List<CategoryEntity> findAllByVisibleTrueOrderByOrderNumberAsc();
    Boolean existsByKey(String key);


}
