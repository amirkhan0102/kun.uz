package dastrulash.uz.kun.uz.repository;

import dastrulash.uz.kun.uz.entity.ArticleSectionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleSectionRepository extends CrudRepository<ArticleSectionEntity, Integer> {

    void deleteByArticleId(String articleId);

    List<ArticleSectionEntity> findByArticleId(String articleId);
}