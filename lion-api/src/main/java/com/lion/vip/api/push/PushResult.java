/**
 * FileName: PushResult
 * Author:   Ren Xiaotian
 * Date:     2018/11/21 14:35
 */

package com.lion.vip.api.push;

import com.lion.vip.api.router.ClientLocation;

import java.util.Arrays;

/**
 * 推送结果
 */
public class PushResult {
    private static final int CODE_SUCCESS = 1;
    private static final int CODE_FAILURE = 2;
    private static final int CODE_OFFLINE = 3;
    private static final int CODE_TIMEOUT = 4;

    private int resultCode;
    private String userId;
    private Object[] timeLine;
    private ClientLocation location;


    public int getResultCode() {
        return resultCode;
    }

    public PushResult setResultCode(int resultCode) {
        this.resultCode = resultCode;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public PushResult setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Object[] getTimeLine() {
        return timeLine;
    }

    public PushResult setTimeLine(Object[] timeLine) {
        this.timeLine = timeLine;
        return this;
    }

    public ClientLocation getLocation() {
        return location;
    }

    public PushResult setLocation(ClientLocation location) {
        this.location = location;
        return this;
    }

    public String getResultDesc() {
        switch (resultCode) {
            case CODE_SUCCESS:
                return "success";
            case CODE_FAILURE:
                return "failure";
            case CODE_OFFLINE:
                return "offline";
            case CODE_TIMEOUT:
                return "timeout";
        }
        return Integer.toString(CODE_TIMEOUT);
    }

    @Override
    public String toString() {
        return "PushResult{" +
                "resultCode=" + resultCode +
                ", userId='" + userId + '\'' +
                ", timeLine=" + Arrays.toString(timeLine) +
                ", location=" + location +
                '}';
    }
}