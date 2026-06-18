package dasturlash.uz.service;

import dasturlash.uz.dto.article.ArticleLikeDTO;
import dasturlash.uz.entity.ArticleLikeEntity;
import dasturlash.uz.enums.LikeStatus;
import dasturlash.uz.repository.ArticleLikeRepository;
import lombok.Lombok;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ArticleLikeService {


    @Autowired
    private ArticleLikeRepository articleLikeRepository;

// like va dislike uchun bita metod yozilgan
// statusni tekshirib like dislike qilib ketaveradi

    public String like(ArticleLikeDTO dto, Integer profileId) {
        Optional<ArticleLikeEntity> optinal = articleLikeRepository.findByArticleIdAndProfileId(dto.getArticleId(), profileId);


        if (optinal.isPresent()) {
            ArticleLikeEntity articleLikeEntity = optinal.get();
            if (articleLikeEntity.getStatus().equals(dto.getStatus())) {
                articleLikeRepository.delete(articleLikeEntity);
                return "Reaksiya qaytarib olindi ";
            }
            // LIKE - DISLIKE yoki otherway
            articleLikeEntity.setStatus(dto.getStatus());
            articleLikeEntity.setUpdatedDate(LocalDateTime.now());
            articleLikeRepository.save(articleLikeEntity);

            return "Reaksiya o'zgartirildi ";
        }

        // birinchi marta rekasiya bosilyotgan bo'lsa
        ArticleLikeEntity entity = new ArticleLikeEntity();
        entity.setArticleId(dto.getArticleId());
        entity.setProfileId(profileId);
        entity.setStatus(dto.getStatus());
        entity.setCreatedDate(LocalDateTime.now());
        articleLikeRepository.save(entity);

        return "Emotsiya bildirildi";
    }


    // likes count

    public Long getLikeCount(String articleId) {
        return articleLikeRepository.countByArticleIdAndStatus(articleId, LikeStatus.LIKE);

    }

    // dislike count

    public Long getDislikeCount(String articleId) {
        return articleLikeRepository.countByArticleIdAndStatus(articleId, LikeStatus.DISLIKE);
    }


}
