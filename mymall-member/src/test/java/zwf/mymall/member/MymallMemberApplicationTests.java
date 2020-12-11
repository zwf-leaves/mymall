package zwf.mymall.member;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zwf.mymall.member.entity.MemberLevelEntity;
import zwf.mymall.member.service.MemberLevelService;

import javax.xml.ws.Action;
import java.util.List;

@SpringBootTest
class MymallMemberApplicationTests {

    @Autowired
    MemberLevelService service;
    @Test
    void contextLoads() {
//        MemberLevelEntity entity = new MemberLevelEntity();
//        entity.setName("zwf");
//        entity.setNote("verygood");
//        service.save(entity);
        List<MemberLevelEntity> list = service.list(new QueryWrapper<MemberLevelEntity>());
        list.forEach((item)->{
            System.out.println(item);
        });
    }


}
