package dasturlash.uz.dto.article;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleShortInfoDTO {
    private String id;
    private String title;
    private String description;
    private String imageId;
    private LocalDateTime publishedDate;
}
