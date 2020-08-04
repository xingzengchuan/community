package answer.question.community.controller;


import answer.question.community.dto.NotificationDTO;
import answer.question.community.dto.PaginationDTO;
import answer.question.community.mapper.QuestionMapper;
import answer.question.community.mapper.UserMapper;
import answer.question.community.model.User;
import answer.question.community.service.NotificationService;
import answer.question.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    //只接受get方式的请求
    @GetMapping("/profile/{action}") //动态切换路径
    public String profile(@PathVariable(name = "action") String action,
                          HttpServletRequest request,
                          Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {

        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        if ("questions".contains(action)) {
            //左边：“我的问题”
            model.addAttribute("section", "questions");
            //右边：“我的问题”每一条数据
            model.addAttribute("sectionName", "我的提问");
            PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("pagination", paginationDTO);
        } else if ("replies".contains(action)) {
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("section", "replies");
            model.addAttribute("pagination",paginationDTO);

            model.addAttribute("sectionName", "最新回复");
        }
        return "profile";
    }
}
