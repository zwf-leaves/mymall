package zwf.mymall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zwf.mymall.product.entity.PmsBrandEntity;
import zwf.mymall.product.service.PmsBrandService;

import java.util.List;

@SpringBootTest
class MymallProductApplicationTests {


    @Autowired
    PmsBrandService service;

    @Test
    void contextLoads() {
        PmsBrandEntity entity = new PmsBrandEntity();
//        entity.setLogo("Z");
//        entity.setName("zwf");
//        entity.setDescript("verygood");
//        service.save(entity);
//        entity.setBrandId(1l);
//        entity.setName("华为");
//        entity.setDescript("还可以");
//        service.updateById(entity);
        List<PmsBrandEntity> brand_id = service.list(new QueryWrapper<PmsBrandEntity>().eq("brand_id", 1));
        System.out.println(brand_id);
    }

}
