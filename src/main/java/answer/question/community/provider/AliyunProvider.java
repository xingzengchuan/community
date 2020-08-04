package answer.question.community.provider;

import answer.question.community.exception.CustomizeErrorCode;
import answer.question.community.exception.CustomizeException;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
public class AliyunProvider {
    @Value("${aliyun.alifile.endpoint}")
    private String endpoint;
    @Value("${aliyun.alifile.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.alifile.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.alifile.bucketName}")
    private String bucketName;
    String objectName = "<yourObjectName>";

    // 上传文件
    public String upload(InputStream fileStream, String fileName) {
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //避免文件重名
        String generatedFileName = "";
        String[] filePaths = fileName.split("\\.");
        if(filePaths.length>1){
            generatedFileName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length-1];
        }else {
            return null;
        }
        try {
            //使用阿里云的流上传
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, fileName, fileStream);
            //设置图片过期时间
            Date expiration = new Date(new Date().getTime() + 60 * 60  * 24 * 365 * 2);
            //获取图片对应的url
            URL url = ossClient.generatePresignedUrl(bucketName , generatedFileName, expiration);
            return url.toString();
        }catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorMessage());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
            return  null;
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
            return null;
        } catch (Throwable e) {
            e.printStackTrace();
            //抛出文件上传失败异常
            throw new CustomizeException(CustomizeErrorCode.FILE_NOT_FOUND);
        } finally {
            ossClient.shutdown();
        }
    }
}
