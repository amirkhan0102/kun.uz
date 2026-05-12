package dastrulash.uz.kun.uz.repository;

import dastrulash.uz.kun.uz.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRoleRepository extends JpaRepository<ProfileEntity, Long> {

    List<ProfileEntity> findByProfileId(Long id);

    void deleteByProfileId(Long id);


}
