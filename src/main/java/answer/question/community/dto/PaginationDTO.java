package answer.question.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious; //当前页面是否展示"<"上一页按钮
    private boolean showFirstPage; //是否展示“<<”首页按钮
    private boolean showNext; //当前页面是否展示">"下一页按钮
    private  boolean showEndPage; //是否展示“>>”尾页按钮

    private  Integer page;//当前页面是第几页
    private List<Integer> pages = new ArrayList<>(); //当前展示在前端的所有page
    private Integer totalPage;

    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage = totalPage;
        this.page = page;
        //pages加上当前页面
        pages.add(page);
        //判断每个当前page展示的页面数
        for(int i=1;i<=3;i++){
            //如果存在，放当前page的前三页，每次往page前面加
            if(page-i>0){
                pages.add(0,page-i);
            }
            //如果存在，放当前page的后三页,每次往page尾部加
            if(page+i<=totalPage){
                pages.add(page+i);
            }
        }

        //是否展示"<"上一页按钮
        if(page == 1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }
        //是否展示">"下一页按钮
        if(page == totalPage){
            showNext = false;
        }else{
            showNext = true;
        }

        //是否展示首页
        if(pages.contains(1)){
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }
        //是否展示尾页
        if(pages.contains(totalPage)){
            showEndPage = false;
        }else {
            showEndPage = true;
        }


    }
}
