package answer.question.community.service;

import answer.question.community.dto.PaginationDTO;
import answer.question.community.dto.QuestionDTO;
import answer.question.community.dto.QuestionQueryDTO;
import answer.question.community.exception.CustomizeErrorCode;
import answer.question.community.exception.CustomizeException;
import answer.question.community.mapper.QuestionExtMapper;
import answer.question.community.mapper.QuestionMapper;
import answer.question.community.mapper.UserMapper;
import answer.question.community.model.Question;
import answer.question.community.model.QuestionExample;
import answer.question.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

//有了service,就可以同时使用QuestionMapper以及UserMapper
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;


    //设置社区问答页面的分页逻辑
    public PaginationDTO list(String search, Integer page, Integer size) {
        //使用“|”分割传递进来的search
        if (StringUtils.isNotBlank(search)) {
            String[] tags = StringUtils.split(search, " ");
            String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        }


        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;
        //拿到总的问题条目
        QuestionExample questionExample = new QuestionExample();
//        questionExample.createCriteria()
//                .andCreatorEqualTo(userId);
        //此处需要传search（等同于之前的questionMapper.countByExample(questionExample)，但不同的是
        // search正则匹配出类似问题后还需要实现分页功能，所以需要传page和size）
        //将questionQueryDTO抽取出来，赋予search的值进SQL语句
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        //设置分页数
        paginationDTO.setPagination(totalPage, page);
        //完成page切换逻辑
        //size*(page-1),size为每页的条数，page为每一页
        Integer offset = size * (page - 1); //偏移量
        //设置搜索到的内容的数据条目和页数
        questionQueryDTO.setSize(size);
        questionQueryDTO.setPage(page);
        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        //循环question对象
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();

            BeanUtils.copyProperties(question, questionDTO); //等于questionDTO.setId(question.getId());
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);


        return paginationDTO;
    }

    //设置个人所提问题页面的分页逻辑
    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        //拿到总的问题条目
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(questionExample);
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        //设置分页数
        paginationDTO.setPagination(totalPage, page);

        //完成page切换逻辑
        //size*(page-1),size为每页的条数，page为每一页
        Integer offset = size * (page - 1); //偏移量


        //通过questionMaplist查到question对象
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        //循环question对象
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();

            BeanUtils.copyProperties(question, questionDTO); //等于questionDTO.setId(question.getId());
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);

        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            //创建问题
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setCommentCount(0);
            question.setViewCount(0);
            question.setLikeCount(0);

            questionMapper.insert(question);
        } else {
            //更新问题
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);
        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect((Collectors.toList()));
        return questionDTOS;
    }
}
