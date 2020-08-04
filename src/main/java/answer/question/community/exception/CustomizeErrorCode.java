package answer.question.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(2001,"你找的问题不存在，要不要换一个试试？"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"您还未登录哦！请登录后再进行评论~"),
    SYS_ERROR(2004,"服务莫名冒烟啦，请稍后再来哦~"),
    TYPE_PARAM_WARONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"您操作或回复的评论不存在"),
    CONTENT_IS_EMPTY(2007,"您操作或回复的评论为空"),
    READ_NOTIFICATION_FAIL(2008,"兄弟你这是读别人的信息呢"),
    NOTIFICATION_NOT_FOUND(2009,"消息莫非是不翼而飞了？"),
    FILE_NOT_FOUND(2010,"文件不存在"),
    ;
    private String message;
    private Integer code;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }


}
