package answer.question.community.dto;

import lombok.Data;

//页面传递过来的DTO
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
