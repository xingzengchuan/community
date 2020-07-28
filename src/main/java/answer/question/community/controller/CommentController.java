package answer.question.community.controller;

import answer.question.community.dto.CommentDTO;
import answer.question.community.mapper.CommentMapper;
import answer.question.community.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommentController {

    @Autowired
    private  CommentMapper commentMapper;

    @ResponseBody
    //post请求
    @RequestMapping(value="/comment",method = RequestMethod.POST)
    //@RequestBody自动将传过来的json复制到DTO层实体上去
    public Object post(@RequestBody CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(1);
        commentMapper.insert(comment);
        return null;
    }
}
