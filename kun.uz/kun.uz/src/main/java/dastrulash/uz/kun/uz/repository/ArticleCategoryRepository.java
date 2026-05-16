package dastrulash.uz.kun.uz.repository;

import dastrulash.uz.kun.uz.entity.ArticleCategoryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleCategoryRepository extends CrudRepository<ArticleCategoryEntity, Integer> {

    void deleteByArticleId(String articleId);

    List<ArticleCategoryEntity> findByArticleId(String articleId);
}