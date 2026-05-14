package dastrulash.uz.kun.uz.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryCreateDTO {

    @NotNull
    private Integer orderNumber;

    @NotBlank
    private String nameUz;

    @NotBlank
    private String nameRu;

    @NotBlank
    private String nameEn;

    @NotBlank(message = "CategoryKey required")
    private String categoryKey;

    @NotBlank
    private String key;
}