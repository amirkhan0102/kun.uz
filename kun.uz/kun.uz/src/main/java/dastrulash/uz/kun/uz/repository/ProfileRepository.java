package dastrulash.uz.kun.uz.repository;

import dastrulash.uz.kun.uz.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    Optional<ProfileEntity> findByUsername(String username);

    Boolean existsByUsername(String username);
}