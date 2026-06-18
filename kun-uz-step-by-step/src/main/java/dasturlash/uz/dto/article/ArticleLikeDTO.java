package dasturlash.uz.dto.article;

import dasturlash.uz.enums.LikeStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleLikeDTO {
    private String articleId;
    private LikeStatus status;

}
