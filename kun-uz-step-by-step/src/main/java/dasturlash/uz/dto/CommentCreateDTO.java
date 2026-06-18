package dasturlash.uz.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateDTO {
    private String articleId;
    private String content;
    private Integer replyId;
}