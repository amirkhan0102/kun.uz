package dastrulash.uz.kun.uz.dto;


import dastrulash.uz.kun.uz.enums.ProfileRoleEnum;
import dastrulash.uz.kun.uz.enums.ProfileStatusEnum;
import lombok.*;


import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileUpdateDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    private ProfileStatusEnum status;

    private List<ProfileRoleEnum> roleList;


}