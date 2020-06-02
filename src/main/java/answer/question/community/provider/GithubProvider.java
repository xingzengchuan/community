package answer.question.community.provider;

import answer.question.community.dto.AccessTokenDTO;
import answer.question.community.dto.GithubUser;
import com.alibaba.fastjson.JSON;
import jdk.nashorn.internal.runtime.JSONFunctions;
import okhttp3.*;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class GithubProvider {
        public String getAccessToken(AccessTokenDTO accessTokenDTO){
            MediaType mediaType = MediaType.get("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO),mediaType);
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                String split[] = string.split("&");
                String token_str = split[0];
                String token = token_str.split("=")[1];
                return token;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        public GithubUser getUser(String accessToken){
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.github.com/user?access_token="+ accessToken)
                    .build();
            try{
                Response response = client.newCall(request).execute();
                String string = response.body().string();
                GithubUser githubUser = JSON.parseObject(string,GithubUser.class);
                return githubUser;
            } catch (Exception e) {
            }
            return null;
        }
}
