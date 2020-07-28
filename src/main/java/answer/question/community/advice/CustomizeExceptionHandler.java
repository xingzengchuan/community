package answer.question.community.advice;

import answer.question.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    //将错误信息反映到页面上：model
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model) {

        //用来测试一个对象是否为一个类的实例:obj instanceof Class
        if(e instanceof CustomizeException){
            model.addAttribute("message",e.getMessage());
        }else {
            model.addAttribute("message","服务莫名冒烟啦，请稍后再来哦~");
        }
        //返回渲染过的页面
        return new ModelAndView("error");
    }

}
