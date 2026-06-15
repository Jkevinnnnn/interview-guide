package interview.guide.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * RustFS (S3兼容) 存储配置属性
 */
@Data  //自动帮你生成getter、setter和tostring的方法
@Component //把这个类交给spring容器管理，然后在其他的地方就可以用@Autowired自动把这个类注入进来
@ConfigurationProperties(prefix = "app.storage") //告诉spring boot去配置文件找所有以app.storage开头的内容，然后进行赋值
public class StorageConfigProperties {

    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String region = "us-east-1";
}
