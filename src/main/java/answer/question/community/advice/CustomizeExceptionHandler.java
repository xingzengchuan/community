package answer.question.community.advice;

import answer.question.community.dto.ResultDTO;
import answer.question.community.exception.CustomizeErrorCode;
import answer.question.community.exception.CustomizeException;
import com.alibaba.fastjson.JSON;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    //将错误信息反映到页面上：model
    ModelAndView handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {

        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            ResultDTO resultDTO;

            //返回JSON
            if(e instanceof CustomizeException){
                resultDTO =  ResultDTO.errorOf((CustomizeException) e);
            }else {
                resultDTO =  ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);

            }

            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));//将object直接变成json
                writer.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return null;
        }else {
            //返回页面
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

}
