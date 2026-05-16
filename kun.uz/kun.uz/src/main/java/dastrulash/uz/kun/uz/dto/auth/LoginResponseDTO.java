package dastrulash.uz.kun.uz.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import dastrulash.uz.kun.uz.enums.ProfileRoleEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponseDTO {

    private String name;
    private String surname;
    private String username;
    private List<ProfileRoleEnum> roleList;
    private PhotoDTO photo;
    private String token;

    @Getter
    @Setter
    public static class PhotoDTO {
        private String id;
        private String url;
    }
}