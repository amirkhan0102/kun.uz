package dastrulash.uz.kun.uz.dto;

import dastrulash.uz.kun.uz.enums.ProfileRoleEnum;
import dastrulash.uz.kun.uz.enums.ProfileStatusEnum;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileInfoDTO {

    private Long id;
    private String name;
    private String surname;
    private String username;
    private List<ProfileRoleEnum> roleList;
    private ProfileStatusEnum status;
    private String photoId;
    private LocalDateTime createdDate;
}