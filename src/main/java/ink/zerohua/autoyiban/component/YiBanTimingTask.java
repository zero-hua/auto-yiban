package ink.zerohua.autoyiban.component;

import ink.zerohua.autoyiban.entity.User;
import ink.zerohua.autoyiban.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class YiBanTimingTask {

    @Resource
    private AutoYiBanBean autoYiBanBean;

    @Resource
    private UserRepository repository;


    @Scheduled(cron = "30 20 05 * * ?")//每天5:20:30打卡
    private void autoYiban1(){
        List<User> users = repository.findAll();
        for(User user:users){
            if ( autoYiBanBean.clockIn(user)) {
                log.info(user.getYibanAccount() + "  打卡成功...");
            }else {
                log.error(user.getYibanAccount() + "  打卡失败...");
            }
        }
    }

    @Scheduled(cron = "30 20 08 * * ?")//每天8:20:30打卡,防止第一次出错
    private void autoYiban2(){
        List<User> users = repository.findAll();
        for(User user:users){
            if ( autoYiBanBean.clockIn(user)) {
                log.info(user.getYibanAccount() + "  打卡成功...");
            }else {
                log.error(user.getYibanAccount() + "  打卡失败...");
            }
        }
    }
}
