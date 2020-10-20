package ink.zerohua.autoyiban.repository;

import ink.zerohua.autoyiban.entity.User;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTest {

    @Resource
    UserRepository repository;

    void testGetOne() {
        User user = new User();
        user.setYibanAccount("201803120103");
        System.out.println();
    }

}