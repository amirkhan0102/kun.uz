package dastrulash.uz.kun.uz.repository;

import dastrulash.uz.kun.uz.entity.ArticleEntity;
import dastrulash.uz.kun.uz.enums.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {

    // 5. Get Last N by sectionId
    @Query("SELECT a FROM ArticleEntity a " +
            "INNER JOIN ArticleSectionEntity s ON s.articleId = a.id " +
            "WHERE s.sectionId = :sectionId AND a.status = 'PUBLISHED' AND a.visible = true " +
            "ORDER BY a.createdDate DESC")
    Page<ArticleEntity> findBySectionId(@Param("sectionId") Integer sectionId, Pageable pageable);

    // 6. Get Last 12 except given ids
    @Query("SELECT a FROM ArticleEntity a " +
            "WHERE a.id NOT IN :ids AND a.status = 'PUBLISHED' AND a.visible = true " +
            "ORDER BY a.createdDate DESC")
    Page<ArticleEntity> findLast12ExceptIds(@Param("ids") List<String> ids, Pageable pageable);

    // 7. Get Last N by categoryId
    @Query("SELECT a FROM ArticleEntity a " +
            "INNER JOIN ArticleCategoryEntity c ON c.articleId = a.id " +
            "WHERE c.categoryId = :categoryId AND a.status = 'PUBLISHED' AND a.visible = true " +
            "ORDER BY a.createdDate DESC")
    Page<ArticleEntity> findByCategoryId(@Param("categoryId") Integer categoryId, Pageable pageable);

    // 8. Get Last N by regionId
    @Query("SELECT a FROM ArticleEntity a " +
            "WHERE a.regionId = :regionId AND a.status = 'PUBLISHED' AND a.visible = true " +
            "ORDER BY a.createdDate DESC")
    Page<ArticleEntity> findByRegionId(@Param("regionId") Integer regionId, Pageable pageable);

    // 9. Get by id
    Optional<ArticleEntity> findByIdAndVisibleTrue(String id);
}