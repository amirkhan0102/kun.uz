package dasturlash.uz.repository;

import dasturlash.uz.entity.SavedArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SavedArticleRepository extends CrudRepository<SavedArticleEntity, Integer> {
    Optional<SavedArticleEntity> findByArticleIdAndProfileId(String articleId, Integer profileId);

    @Query("FROM SavedArticleEntity WHERE profileId = ?1 ORDER BY createdDate DESC")
    Page<SavedArticleEntity> findByProfileId(Integer profileId, Pageable pageable);
}
