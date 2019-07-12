package com.yun.yunwsserver.module.wesocket.mq.dispatch;

public abstract class DispatchTask implements Runnable {

    protected int dispatchKey;

    public int getDispatchKey() {
        return dispatchKey;
    }
}