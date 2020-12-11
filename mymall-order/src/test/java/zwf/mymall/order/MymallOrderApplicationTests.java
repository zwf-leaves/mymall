package zwf.mymall.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zwf.mymall.order.entity.OrderEntity;
import zwf.mymall.order.service.OrderService;

import java.util.List;

@SpringBootTest
class MymallOrderApplicationTests {


    @Autowired
    OrderService service;

    @Test
    void contextLoads() {
//        OrderEntity entity = new OrderEntity();
//        entity.setPayType(2);
//        entity.setBillContent("zwf");
//        service.save(entity);
        List<OrderEntity> list = service.list(new QueryWrapper<OrderEntity>());
        list.forEach((item) -> {
            System.out.println(item);
        });
    }

}
