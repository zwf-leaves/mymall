package zwf.mymall.coupon;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zwf.mymall.coupon.entity.CouponEntity;
import zwf.mymall.coupon.service.CouponService;

import java.util.List;

@SpringBootTest
class MymallCouponApplicationTests {


    @Autowired
    CouponService service;

    @Test
    void contextLoads() {
//        CouponEntity entity = new CouponEntity();
//        entity.setCode("200");
//        entity.setCouponName("zwf");
//        service.save(entity);
        List<CouponEntity> list = service.list(new QueryWrapper<CouponEntity>());
        System.out.println(list);

    }

}
