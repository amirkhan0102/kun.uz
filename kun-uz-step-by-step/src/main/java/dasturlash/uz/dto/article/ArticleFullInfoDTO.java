package dasturlash.uz.dto.article;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleFullInfoDTO {
    private String id;
    private String title;
    private String description;
    private String content;
    private String imageId;
    private Integer readTime;
    private Integer viewCount;
    private Long sharedCount;
    private LocalDateTime publishedDate;

    private String regionName;
    private String categoryName;
    private String sectionName;
}