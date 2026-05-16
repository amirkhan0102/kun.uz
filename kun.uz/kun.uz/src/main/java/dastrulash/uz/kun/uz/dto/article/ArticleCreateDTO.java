package dastrulash.uz.kun.uz.dto.article;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
public class ArticleCreateDTO {

    @NotBlank(message = "Title required")
    private String title;

    @NotBlank(message = "Description required")
    private String description;

    @NotBlank(message = "Content required")
    private String content;

    private String imageId;

    private Integer regionId;

    @NotNull(message = "Category required")
    private List<Integer> categoryIds;

    @NotNull(message = "Section required")
    private List<Integer> sectionIds;

    private Integer readTime;
}