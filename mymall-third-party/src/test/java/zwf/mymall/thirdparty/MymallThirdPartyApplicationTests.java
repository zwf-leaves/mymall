package zwf.mymall.thirdparty;

import com.aliyun.oss.OSSClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
class MymallThirdPartyApplicationTests {


    @Autowired
    OSSClient ossClient;
    @Test
    void contextLoads() {
    }
    @Test
    public void upload(){
//        // Endpoint以杭州为例，其它Region请按实际情况填写。
//        String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
//// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
//        String accessKeyId = "LTAI4GGyAtbk6CXkvQNpg4uD";
//        String accessKeySecret = "dS6C71RenRNYtnNtPAbyF1HASZJa8M";
//
// //创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 上传文件流。
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("C:\\Users\\茵羽\\Pictures\\Saved Pictures\\u=1247918316,99922912&fm=26&gp=0.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ossClient.putObject("zwf-mymall", "zwf1.jpg", inputStream);

// 关闭OSSClient。
        ossClient.shutdown();
        System.out.println("成功");
    }

}
