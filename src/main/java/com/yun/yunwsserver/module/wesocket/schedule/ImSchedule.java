package com.yun.yunwsserver.module.wesocket.schedule;

import com.yun.yunwsserver.module.wesocket.session.ImSessionManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * The itemType Im schedule.
 * @author: yun
 * @createdOn: 2018 /8/7 13:44.
 */
@Component
public class ImSchedule {

    /**
     * 清楚缓存的 session
     * 每60秒执行一次
     */
    @Scheduled(fixedDelay = 10000)
    public void cleanCacheSession() {
        int count = ImSessionManager.cleanCacheData();

        System.out.println("清除缓存：" + count);
        System.out.println("当前在线用户：" + ImSessionManager.getOnlineNum());
        System.out.println("缓存中的用户个数：" + ImSessionManager.getCacheNum());
    }
}
