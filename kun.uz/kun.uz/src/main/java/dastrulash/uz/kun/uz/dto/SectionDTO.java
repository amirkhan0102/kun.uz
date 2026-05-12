package dastrulash.uz.kun.uz.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionDTO {

    private Long id;
    private String key;
    private String name; // tilga qarab nameUz/nameRu/nameEn
}