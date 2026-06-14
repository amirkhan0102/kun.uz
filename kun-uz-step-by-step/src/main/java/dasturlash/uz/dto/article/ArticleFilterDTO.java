package dasturlash.uz.dto.article;


import dasturlash.uz.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleFilterDTO {

    private String title;
    private Integer categoryId;
    private Integer regionId;
    private ArticleStatus status;
    private Integer publisherId;
    private Integer moderatorId;


}
