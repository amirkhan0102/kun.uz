package dasturlash.uz.repository;

import dasturlash.uz.entity.CommentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CommentRepository extends CrudRepository<CommentEntity, Integer> {

    @Query("FROM CommentEntity WHERE articleId = ?1 AND visible = true ORDER BY createdDate DESC")
    Page<CommentEntity> findByArticleId(String articleId, Pageable pageable);

    Optional<CommentEntity> findByIdAndVisibleTrue(Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE CommentEntity SET visible = false WHERE id = ?1")
    void deleteComment(Integer id);
}