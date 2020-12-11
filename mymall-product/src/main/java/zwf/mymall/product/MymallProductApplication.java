package zwf.mymall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/*
* 整合mybatis-plus
*    1）、导入依赖
*         <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus</artifactId>
            <version>3.4.1</version>
        </dependency>
*     2）、配置
*        1、配置数据源
*        2、配置mybatis-plus相关信息
*
*
*
* */
@EnableDiscoveryClient
@MapperScan("zwf.mymall.product.dao")
@SpringBootApplication
public class MymallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(MymallProductApplication.class, args);
    }

}
