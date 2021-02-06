package zwf.mymall.ware;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zwf.mymall.ware.entity.WareInfoEntity;
import zwf.mymall.ware.service.WareInfoService;

import java.util.List;

@SpringBootTest
class MymallWareApplicationTests {


    @Autowired
    WareInfoService service;
    @Test
    void contextLoads() {
//        WareInfoEntity entity = new WareInfoEntity();
//        entity.setName("zwf");
//        service.save(entity);
        List<WareInfoEntity> list = service.list(new QueryWrapper<WareInfoEntity>());
        list.forEach((item)->{
            System.out.println(item);
        });

    }

}
