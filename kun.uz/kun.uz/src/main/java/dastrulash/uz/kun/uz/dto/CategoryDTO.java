package dastrulash.uz.kun.uz.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {

    private Long id;
    private Integer orderNumber;
    private String key;
    private String name; // tilga qarab nameUz/nameRu/nameEn
}