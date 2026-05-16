package dastrulash.uz.kun.uz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SectionDTO {
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

    @NotBlank(message = "SectionKey required")
    private String sectionKey;

    private LocalDateTime createdDate;
    private String name;
}
