package dasturlash.uz.repository;

import dasturlash.uz.entity.ArticleLikeEntity;
import dasturlash.uz.enums.LikeStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ArticleLikeRepository extends CrudRepository<ArticleLikeEntity, Integer> {
    Optional<ArticleLikeEntity> findByArticleIdAndProfileId(String articleId, Integer profileId);

    @Query("SELECT COUNT(a) FROM ArticleLikeEntity a WHERE a.articleId = ?1 AND a.status = ?2")
    Long countByArticleIdAndStatus(String articleId, LikeStatus status);

}
