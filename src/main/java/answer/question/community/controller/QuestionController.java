package answer.question.community.controller;

import answer.question.community.dto.CommentDTO;
import answer.question.community.dto.QuestionDTO;
import answer.question.community.enums.CommentTypeEnum;
import answer.question.community.service.CommentService;
import answer.question.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model) {
        //QuestionDTO里有丰富的user模型
        QuestionDTO questionDTO = questionService.getById(id);
        //相关问题
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        //回复类型：1question;2comment
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

        //累加阅读数数
        questionService.incView(id);
        //使用model将QuestionDTO写到页面去
        model.addAttribute("question", questionDTO);
        //使用model将comments评论写到页面去
        model.addAttribute("comments", comments);
        //把tag标签近似的相关问题放到页面中去
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }

}
