package dasturlash.uz.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDTO {
    private Integer id;
    private String articleId;
    private Integer profileId;
    private String content;
    private Integer replyId;
    private LocalDateTime createdDate;
}