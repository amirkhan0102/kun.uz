package dastrulash.uz.kun.uz.dto;



import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryDTO {

    private Integer id;

    @NotNull(message = "OrderNumber required")
    @Min(value = 1, message = "OrderNumber have to higher than 0")
    private Integer orderNumber;

    @NotBlank(message = "NameUz required")
    private String nameUz;

    @NotBlank(message = "NameRu required")
    private String nameRu;

    @NotBlank(message = "NameEn required")
    private String nameEn;

    @NotBlank(message = "CategoryKey required")
    private String categoryKey;

    private LocalDateTime createdDate;
    private String name;
}
