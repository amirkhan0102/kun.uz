package dastrulash.uz.kun.uz.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileUpdatePhotoDTO {

    @NotBlank(message = "PhotoId required")
    private String photoId;
}
