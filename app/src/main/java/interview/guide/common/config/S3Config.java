package interview.guide.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;


//连接java程序和rustfs的桥梁代码，创建s3client对象，java代码可以实现真正的上传、下载文件
/**
 * S3客户端配置（用于RustFS）
 */
@Configuration  //告诉spring这是一个配置类，启动时会运行里面的逻辑
@RequiredArgsConstructor
public class S3Config {

    private final StorageConfigProperties storageConfig;

    //把创建好的上client交给spring管理器，然后在任何地方想要操作文件，只需要@autowired这个s3client就可以
    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
            storageConfig.getAccessKey(),
            storageConfig.getSecretKey()
        );

        return S3Client.builder()
            .endpointOverride(URI.create(storageConfig.getEndpoint()))
            .region(Region.of(storageConfig.getRegion()))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .forcePathStyle(true) // 关键配置：使用路径风格访问，否则 SDK 会使用虚拟主机风格（`bucket.endpoint`）导致 DNS 解析失败
            .build();
    }
}
