package ink.zerohua.autoyiban.repository;

import ink.zerohua.autoyiban.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: auto-yiban
 * @description: 操作用户的接口
 * @author: zerohua
 * @create: 2020-10-17 03:45
 **/
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
