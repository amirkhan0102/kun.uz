package dasturlash.uz.repository;

import dasturlash.uz.entity.CommentLikeEntity;
import dasturlash.uz.enums.LikeStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity, Integer> {

    Optional<CommentLikeEntity> findByCommentIdAndProfileId(Integer commentId, Integer profileId);

    @Query("SELECT COUNT(c) FROM CommentLikeEntity c WHERE c.commentId = ?1 AND c.status = ?2")
    Long countByCommentIdAndStatus(Integer commentId, LikeStatus status);
}