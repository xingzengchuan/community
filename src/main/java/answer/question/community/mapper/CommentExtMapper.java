package answer.question.community.mapper;

import answer.question.community.model.Comment;
import answer.question.community.model.Question;
import org.springframework.stereotype.Component;

@Component
public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}