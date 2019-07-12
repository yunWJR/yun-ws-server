package com.yun.yunwsserver;

import com.yun.yunwsserver.module.wesocket.nettyws.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling

public class YunWsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(YunWsServerApplication.class, args);
    }

    @PostConstruct
    public void initNetty() {
        new Thread() {
            @Override
            public void run() {
                new NettyServer().run(7191);
            }
        }.start();
    }
}
