package dastrulash.uz.kun.uz.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionInfoDTO {
    private long id;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String key;
    private String imageId;
    private Integer orderNumber;
    private Boolean visible;
    private LocalDateTime createdDate;

}
