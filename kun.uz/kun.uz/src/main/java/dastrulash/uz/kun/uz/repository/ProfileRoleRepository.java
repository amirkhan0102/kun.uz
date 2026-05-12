package dastrulash.uz.kun.uz.repository;

import dastrulash.uz.kun.uz.entity.ProfileEntity;
import dastrulash.uz.kun.uz.entity.ProfileRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRoleRepository extends JpaRepository<ProfileRoleEntity, Long> {

    List<ProfileRoleEntity> findByProfileId(Long id);

    void deleteByProfileId(Long id);


}
