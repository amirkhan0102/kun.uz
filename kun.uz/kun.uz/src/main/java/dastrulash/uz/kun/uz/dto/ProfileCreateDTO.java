package dastrulash.uz.kun.uz.dto;


import dastrulash.uz.kun.uz.enums.ProfileRoleEnum;
import dastrulash.uz.kun.uz.enums.ProfileStatusEnum;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileCreateDTO {


    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @Email
    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 6)
    private String password;

    private ProfileStatusEnum status;

    private List<ProfileRoleEnum> roleList;



}
