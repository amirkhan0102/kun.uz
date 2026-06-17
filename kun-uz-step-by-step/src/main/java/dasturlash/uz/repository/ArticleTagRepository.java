package dasturlash.uz.repository;

import dasturlash.uz.entity.tag.ArticleTagEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleTagRepository extends CrudRepository<ArticleTagEntity,Integer> {

    @Query("SELECT tagId FROM ArticleTagEntity WHERE articleId= ?1")
    List<Integer> getTagIdListByArticleId(String articleId);


    @Transactional
    @Modifying
    @Query("DELETE FROM ArticleTagEntity where articleId=?1")
    void deleteByArticleId(String articleId);


    @Query("SELECT articleId FROM ArticleTagEntity WHERE tagId = ?1")
    List<String> getArticleIdListByTagId(String tagId);


}
