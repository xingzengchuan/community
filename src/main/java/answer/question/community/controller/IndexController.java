package answer.question.community.controller;

import answer.question.community.dto.PaginationDTO;
import answer.question.community.dto.QuestionDTO;
import answer.question.community.mapper.QuestionMapper;
import answer.question.community.mapper.UserMapper;
import answer.question.community.model.Question;
import answer.question.community.model.User;
import answer.question.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "search",required = false) String search //制作搜索功能，传搜索参数
    ) {
        PaginationDTO pagination = questionService.list(search,page, size);
        model.addAttribute("pagination", pagination);
        //页面搜索有内容就把search传进去，避免点击页数时返回总的问题页面
        model.addAttribute("search",search);
        return "index";
    }
}
