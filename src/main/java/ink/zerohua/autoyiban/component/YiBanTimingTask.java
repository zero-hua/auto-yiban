package ink.zerohua.autoyiban.component;

import ink.zerohua.autoyiban.entity.User;
import ink.zerohua.autoyiban.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zerohua
 * @desc $END$ YiBanTimingTask.java
 * @date 2020-10-19 17:16
 * @logs[0] 2020-10-19 17:16 zerohua 创建了YiBanTimingTask.java文件
 */
@Configuration
@EnableScheduling
public class YiBanTimingTask {

    @Resource
    private AutoYiBanBean autoYiBanBean;

    @Resource
    private UserRepository repository;


    @Scheduled(cron = "30 25 06 * * ?")//每天6:25:30打卡
    private void autoYiban(){
        List<User> users = repository.findAll();
        for(User user:users){
            if ( autoYiBanBean.clockIn(user)) {
                System.out.println(user.getYibanAccount() + "打卡成功...");
            }else {
                System.out.println(user.getYibanAccount() + "打卡失败...");
            }
        }
    }
}
