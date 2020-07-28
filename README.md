## Q&A community

## 资料
[spring 文档](https://spring.io/)

[spring web](http://spring.io/guides/gs/serving-web-content/)
[es](https://elasticsearch.cn/explore)
[git_deploy_key](http://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)
[gitub_oauth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)
[bootstrap](http://v3.bootcss.com/getting-started)
[spring boot](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#boot-features-embedded-database-support)
[Thymeleaf](https://www.thymeleaf.org/documentation.html
[Spring MVC](https://docs.spring.io/spring/docs/5.0.3.RELEASE/spring-framework-reference/web.html#mvc)
## 工具
[git](https://git-scm.com/download)
[visual_Paradigm](http://www.visual-paradigm.com)
[lombok](https://projectlombok.org/(自动生成get/set))
## 脚本
'''sql
CREATE TABLE USER
(
  ID int AUTO_INCREMENT PRIMARY KEY NOT NULL,
  ACCOUNT_ID   VARCHAR(100),
  NAME         VARCHAR(50),
  TOKEN        CHAR(36),
  GMT_CREATE   BIGINT,
  GMT_MODIFIED BIGINT
);
'''
bash:
mvn flyway:migrate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
'''

'''