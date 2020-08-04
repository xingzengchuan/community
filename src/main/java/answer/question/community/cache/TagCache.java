package answer.question.community.cache;

import answer.question.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("php","java","node.js","python","c++","c","golang","spring","后端","springboot","django","flask","c#","swoole","ruby","asp.net","ruby-on-rails","scala","rust","lavarel","爬虫"));
        tagDTOS.add(program);

        TagDTO framwork = new TagDTO();
        framwork.setCategoryName("平台框架");
        framwork.setTags(Arrays.asList("laravel","spring","express","django","flask","yii","ruby-on-rails","tornado","struts"));
        tagDTOS.add(framwork);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("nginx","docker","ubuntu","centos","缓存","tomcat","负载均衡","unix","hadoop","windows-server"));
        tagDTOS.add(server);

        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql","redis","mongodb","sql","oracle","nosql memcached","sqlserver","postgresql","sqlite"));
        tagDTOS.add(db);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git","github","macos","visual-studio-code","windows","vim","sublime-text","intellij-idea","phpstorm","eclipse","webstorm","编辑器","svn","visual-studio","pycharm","emacs"));
        tagDTOS.add(tool);

        return tagDTOS;
    }

    public static String filterInValid(String tags){
        String[] split = StringUtils.split(tags,",");
        List<TagDTO> tagDTOS = get();
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        //过滤掉不在tagList里的
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}