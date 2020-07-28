package answer.question.community.controller;


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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PublishController {
    //接收完参数后，注入QuestionService
    @Autowired
    private QuestionService questionService;

    //点击页面的时候，传过来一个id,通过id获取当前question
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,
                       Model model){
        QuestionDTO question = questionService.getById(id);
        //回显到页面
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        //跳转到具体问题页面的时候，添加唯一标识id
        model.addAttribute("id",question.getId());
        return "publish";
    }

    //get:渲染页面，post执行请求
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            //接收mdel获取到的参数
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value ="description",required = false) String description,
            @RequestParam(value ="tag",required = false) String tag,
            @RequestParam(value = "id",required = false) Integer id,
            HttpServletRequest request,
            Model model) {

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        //页面输入判断

        if (title == null ||title.equals("")){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }

        if (description == null ||description .equals("")){
            model.addAttribute("error","描述不能为空");
            return "publish";
        }

        if (tag == null ||tag.equals("")){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);

        //创建或者编辑修改问题
        questionService.createOrUpdate(question);

        return "redirect:/";
    }
}
