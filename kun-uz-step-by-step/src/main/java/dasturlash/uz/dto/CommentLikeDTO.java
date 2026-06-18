package dasturlash.uz.dto;

import dasturlash.uz.enums.LikeStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentLikeDTO {
    private Integer commentId;
    private LikeStatus status;
}