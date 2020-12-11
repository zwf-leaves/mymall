package zwf.mymall.coupon;

import com.alibaba.nacos.api.naming.utils.NamingUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@MapperScan("zwf.mymall.coupon.dao")
@SpringBootApplication
public class MymallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(MymallCouponApplication.class, args);

    }

}
