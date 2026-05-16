package dastrulash.uz.kun.uz.dto.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleFullInfoDTO {

    private String id;
    private String title;
    private String description;
    private String content;
    private Integer sharedCount;
    private Integer viewCount;
    private String imageId;
    private LocalDateTime publishedDate;

    private RegionDTO region;
    private List<CategoryDTO> categoryList;
    private List<SectionDTO> sectionList;

    @Getter
    @Setter
    public static class RegionDTO {
        private String key;
        private String name;
    }

    @Getter
    @Setter
    public static class CategoryDTO {
        private String key;
        private String name;
    }

    @Getter
    @Setter
    public static class SectionDTO {
        private Integer id;
        private String name;
    }
}