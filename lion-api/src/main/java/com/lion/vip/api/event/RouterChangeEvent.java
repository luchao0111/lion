/**
 * FileName: RouterChangeEvent
 * Author:   Ren Xiaotian
 * Date:     2018/11/21 13:56
 */

package com.lion.vip.api.event;

import com.lion.vip.api.router.Router;

/**
 * 路由变更事件
 */
public final class RouterChangeEvent implements Event {
    private final String userId;      //用户ID
    private final Router<?> router;   //路由信息

    public RouterChangeEvent(String userId, Router<?> router) {
        this.userId = userId;
        this.router = router;
    }
}