package answer.question.community.controller;

import answer.question.community.dto.QuestionDTO;
import answer.question.community.mapper.QuestionMapper;
import answer.question.community.model.Question;
import answer.question.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model) {
        //QuestionDTO里有丰富的user模型
        QuestionDTO questionDTO = questionService.getById(id);
        //累加阅读数数
        questionService.incView(id);
        //使用model将QuestionDTO写到页面去
        model.addAttribute("question", questionDTO);
        return "question";
    }
}
