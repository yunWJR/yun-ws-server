package com.yun.yunwsserver.module.wesocket.session;

import com.yun.yunwsserver.module.wesocket.model.ImWsSession;
import com.yun.yunwsserver.module.wesocket.model.ImWsSessionUser;
import com.yun.yunwsserver.module.wesocket.model.WsRspMessageType;
import com.yun.yunwsserver.module.wesocket.mq.ImRspManager;

import java.util.*;

/**
 * ImSession管理，管理所有 session。
 * <p>
 * 每个平台一个 session 管理器，不同平台 session 可以共存。
 * <p>
 * 后期要考虑持久化，以及分布式共享。
 * @author: yun
 * @createdOn: 2018 /7/26 09:03.
 */
public class ImSessionManager {

    // region --Field

    /**
     * sesion 管理列表
     */
    private static Map<String, ImPlatformSessionManager> userSessionMap = new HashMap<>();

    // endregion

    // region --Constructor

    /**
     * Instantiates a new Im session manager.
     */
    public ImSessionManager() {
    }

    // endregion

    // region --static method

    /**
     * 添加用户 session
     * @param sessionId
     * @param para      the para
     * @param session   the session
     * @return the boolean
     */
    public synchronized static boolean addUser(String sessionId, String clientPt, String para, ImWsSession session) {
        // 保存当前平台
        ImPlatformSessionManager curMg = getValidSessionByUser(sessionId);

        curMg.addUser(clientPt, para, session);

        return true;
    }

    public static ImWsSessionUser getSocketUserByUserId(String userId, String platformType) {
        ImPlatformSessionManager mg = userSessionMap.get(userId);

        if (mg != null) {
            return mg.getUserByPt(platformType);
        }

        return null;
    }

    public static List<ImWsSessionUser> getSocketUserByUserId(String userId) {
        ImPlatformSessionManager mg = userSessionMap.get(userId);

        if (mg != null) {
            return mg.allOnlineUser();
        }

        return new ArrayList<>();
    }

    /**
     * 清除缓存的无效 session
     * @return 清楚的数量
     */
    public static int cleanCacheData() {
        int cleanCount = 0;
        for (ImPlatformSessionManager mg : userSessionMap.values()) {
            cleanCount += mg.cleanCacheData(120);
        }

        return cleanCount;
    }

    /**
     * 根据sessonId获取缓存信息
     * @param sessionId the session pkId
     * @return socket userservice by session pkId
     */
    public static ImWsSessionUser getSocketUserBySessionId(String sessionId) {

        ImWsSessionUser su = null;
        for (ImPlatformSessionManager mg : userSessionMap.values()) {

            su = mg.getSocketUserBySessionId(sessionId);
            if (su != null) {
                return su;
            }
        }

        return su;
    }

    /**
     * 获取在线人数
     * @return online num
     */
    public static long getOnlineNum() {
        int online = 0;
        for (ImPlatformSessionManager mg : userSessionMap.values()) {

            online += mg.getOnlineNum();
        }

        return online;
    }

    /**
     * 获取缓存数量
     * @return cash num
     */
    public static int getCacheNum() {
        int cash = 0;
        for (ImPlatformSessionManager mg : userSessionMap.values()) {

            cash += mg.getCacheNum();
        }

        return cash;
    }

    /**
     * Update heart.
     * @param session the session
     */
    public static void updateHeart(ImWsSession session) {
        ImWsSessionUser su = getSocketUserBySessionId(session.getId());
        if (su == null) {
            ImRspManager.sendErrByType(session, WsRspMessageType.MsgOnSessionErr);
            return;
        }

        su.setLastHeartDate(new Date());
        ImRspManager.sendRsp(session, WsRspMessageType.HeartPong, null);
    }

    /**
     * Remove session by pkId.
     * @param sessionId the sessionId
     */
    public static boolean removeSessionById(String sessionId) {
        boolean findSs = false;

        for (ImPlatformSessionManager mg : userSessionMap.values()) {

            findSs = mg.removeSessionById(sessionId);
            if (findSs) {
                return findSs;
            }
        }

        return findSs;
    }

    // endregion   

    // region --Getter and Setter

    // endregion

    // region --Public method

    // endregion

    // region --private method

    /**
     * 获取指定平台的manager
     * @param userId
     * @return
     */
    private static ImPlatformSessionManager getValidSessionByUser(String userId) {
        ImPlatformSessionManager mg = userSessionMap.get(userId);

        if (mg == null) {
            mg = new ImPlatformSessionManager(userId);

            userSessionMap.put(userId, mg);
        }

        return mg;
    }

    // endregion

    // region --Other

    // endregion
}
