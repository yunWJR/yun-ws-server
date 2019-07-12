package com.yun.yunwsserver.module.wesocket.mq.dispatch;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ImDispatcher {

    private final int CORE_SIZE = Runtime.getRuntime().availableProcessors();

    private ExecutorService[] executors = new ExecutorService[CORE_SIZE];

    @PostConstruct
    public void init() {
        for (int i = 0; i < executors.length; i++) {
            ExecutorService executor = Executors.newSingleThreadExecutor(new NamedThreadFactory("im-dispatcher"));
            executors[i] = executor;
        }
    }

    public void addTask(DispatchTask task) {
        // 根据分发id求模映射
        int index = task.getDispatchKey() % executors.length;
        executors[index].submit(task);
    }
}
