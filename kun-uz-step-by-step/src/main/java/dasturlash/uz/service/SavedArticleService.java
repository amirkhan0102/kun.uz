package dasturlash.uz.service;

import dasturlash.uz.dto.article.ArticleShortInfoDTO;
import dasturlash.uz.entity.ArticleEntity;
import dasturlash.uz.entity.ArticleLikeEntity;
import dasturlash.uz.entity.SavedArticleEntity;
import dasturlash.uz.exceptions.AppBadException;
import dasturlash.uz.repository.SavedArticleRepository;
import dasturlash.uz.repository.article.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SavedArticleService {


    @Autowired
    private SavedArticleRepository savedArticleRepository;

    @Autowired
    private ArticleRepository articleRepository;

    // Save and Unsave

    public String save(String articleId, Integer profileId){
        Optional<SavedArticleEntity> optional = savedArticleRepository.findByArticleIdAndProfileId(articleId,profileId);
        if(optional.isPresent()){
            savedArticleRepository.delete(optional.get());
            return "Removed from saved article";
        }
        SavedArticleEntity savedArticle = new SavedArticleEntity();
        savedArticle.setArticleId(articleId);
        savedArticle.setProfileId(profileId);
        savedArticleRepository.save(savedArticle);
        return "Article successfully saved";
    }


    // Get saved articles

    public Page<ArticleShortInfoDTO> getSavedArticles( Integer profileId, int page, int size){
        Page<SavedArticleEntity> saved=savedArticleRepository.findByProfileId(profileId, PageRequest.of(page, size));


        return saved.map(s -> {
            ArticleEntity article = articleRepository.findByIdAndVisibleTrue(s.getArticleId())
                    .orElseThrow(() -> new AppBadException("Article not found"));
            return toShortDTO(article);
        });
    }

    private ArticleShortInfoDTO toShortDTO(ArticleEntity article) {
        ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setDescription(article.getDescription());
        dto.setImageId(article.getImageId());
        dto.setPublishedDate(article.getPublishedDate());
        return dto;
    }


}
