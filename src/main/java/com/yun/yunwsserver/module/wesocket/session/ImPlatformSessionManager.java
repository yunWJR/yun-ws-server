package com.yun.yunwsserver.module.wesocket.session;

import com.yun.yunwsserver.module.wesocket.model.ClientUserStatusType;
import com.yun.yunwsserver.module.wesocket.model.ImWsSession;
import com.yun.yunwsserver.module.wesocket.model.ImWsSessionUser;
import lombok.Data;

import java.util.*;

/**
 * 平台的 session 管理
 */
@Data
public class ImPlatformSessionManager {
    //SynchronizedMap类是定义在Collections中的一个静态内部类。它实现了Map接口，并对其中的每一个方法实现，通过synchronized 关键字进行了同步控制
    // Collections.synchronizedMap 返回的是一个线程安全的hashMap(原hashMap为线程不安全的，需要手动在线程中处理，synchronizedMap省去这一步骤)
    private final Map<String, ImWsSessionUser> ptMap = Collections.synchronizedMap(new HashMap<>());

    private String userSessionId;

    public ImPlatformSessionManager(String userId) {
        this.userSessionId = userId;
    }

    public boolean removeSessionById(String sessionId) {
        for (String key : ptMap.keySet()) {
            ImWsSessionUser user = ptMap.get(key);
            if (user != null && user.getSession().getId().equals(sessionId)) {
                ptMap.remove(key);
                return true;
            }
        }

        return false;
    }

    /**
     * 根据用户id获取缓存信息
     * @param pt 用户id
     * @return
     */
    public ImWsSessionUser getUserByPt(String pt) {
        return ptMap.get(pt);
    }

    public List<ImWsSessionUser> allOnlineUser() {
        List<ImWsSessionUser> list = new ArrayList<>();

        for (ImWsSessionUser user : ptMap.values()) {
            list.add(user);

            // todo
            // if (user.isOnline()) {
            //     list.add(user);
            // }
        }

        return list;
    }

    /**
     * 根据sessonId获取缓存信息
     * @param sessionId
     * @return
     */
    public ImWsSessionUser getSocketUserBySessionId(String sessionId) {
        for (String key : ptMap.keySet()) {
            ImWsSessionUser user = ptMap.get(key);
            if (user != null && user.getSession().getId().equals(sessionId)) {
                return user;
            }
        }

        return null;
    }

    /**
     * 获取在线人数
     * @return
     */
    public long getOnlineNum() {
        long count = 0;

        for (String key : ptMap.keySet()) {
            ImWsSessionUser user = ptMap.get(key);
            if (user != null) {
                if (user.getStatus() == ClientUserStatusType.Hide || user.getStatus() == ClientUserStatusType.Online) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 获取缓存数量
     * @return
     */
    public int getCacheNum() {
        return ptMap.keySet().size();
    }

    /**
     * 清除离线时间超过{timeoutSeconds}秒的缓存
     * @param timeoutSeconds
     */
    public int cleanCacheData(int timeoutSeconds) {
        int cleanCount = 0;

        if (timeoutSeconds > 0) {
            long now = System.currentTimeMillis();

            for (String key : ptMap.keySet()) {
                ImWsSessionUser user = ptMap.get(key);
                if (user != null) {
                    if (user.getSession().isOpen()) {
                        Date date = user.getLastHeartDate();
                        if (date == null) {
                            ptMap.remove(key);
                            cleanCount++;
                        } else {
                            long diff = now - date.getTime();
                            int hour = (int) (diff / (1000));
                            if (hour > timeoutSeconds) {
                                ptMap.remove(key);
                                cleanCount++;
                            }
                        }
                    } else {
                        ptMap.remove(key);
                        cleanCount++;
                    }
                }
            }
        }

        return cleanCount;
    }

    public void addUser(String clientPt, String para, ImWsSession session) {
        ImWsSessionUser user = new ImWsSessionUser(clientPt, para, session);
        user.setStatus(ClientUserStatusType.Online);
        user.setLastHeartDate(new Date());

        ptMap.put(clientPt, user);
    }
}
