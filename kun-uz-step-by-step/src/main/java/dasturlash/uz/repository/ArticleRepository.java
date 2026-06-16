package dasturlash.uz.repository;

import dasturlash.uz.entity.ArticleEntity;
import dasturlash.uz.enums.ArticleStatus;
import dasturlash.uz.mapper.ArticleShortInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {

    @Transactional
    @Modifying
    @Query("Update  ArticleEntity set visible = false where id =?1")
    int delete(String articleId);


    @Transactional
    @Modifying
    @Query("Update ArticleEntity set status = ?2 where id =?1")
    int changeStatus(String articleId, ArticleStatus status);

    // select * from article where id in (select article_id from article_section where section_id = 10)
    // order by created_date desc limit N
    // where visible = true

    // select * from article a
    // inner join article_section ac on ac.article_id = a.id
    // where ac.section_id = ? and a.visible = true and status = ?
    // order by a.created_date desc

    /*@Query("from ArticleEntity where id in (select  articleId from ArticleSectionEntity where sectionId = ?1) " +
            " and visible = true and status = 'PUBLISHED' " +
            " order by createdDate desc  limit ?2")*/
    @Query(" select a.id as id, a.title as title, a.description as description, a.imageId as imageId, a.publishedDate as publishedDate " +
            " from  ArticleEntity a " +
            " inner join ArticleSectionEntity ac on ac.articleId = a.id " +
            " where ac.sectionId = ?1 and a.visible = true and a.status = 'PUBLISHED' " +
            " order by a.createdDate desc  limit ?2")
    List<ArticleShortInfo> getBySectionId(Integer sectionId, int limit);

    @Query("select a from ArticleEntity a where a.id not in :ids " + "and a.visible=true and a.status='PUBLISHED' " + "order by a.publishedDate desc")
    List<ArticleEntity> findLast12ExceptIds(@Param("ids") List<String> ids, Pageable pageable);


    @Query("SELECT a FROM ArticleEntity a " +
            "JOIN ArticleCategoryEntity ac ON ac.articleId = a.id " +
            "WHERE ac.categoryId = :categoryId " +
            "AND a.visible=true AND a.status='PUBLISHED' " +
            "ORDER BY a.publishedDate DESC")
    Page<ArticleEntity> findByCategoryId(@Param("categoryId") Integer categoryId, Pageable pageable);


    @Query("SELECT a FROM ArticleEntity a " +
            "WHERE a.regionId = :regionId " +
            "AND a.visible = true AND a.status = 'PUBLISHED' " +
            "ORDER BY a.publishedDate DESC")
    Page<ArticleEntity> findByRegionId(@Param("regionId") Integer regionId, Pageable pageable);


}