package com.lion.vip.api.spi.push;

import com.lion.vip.api.spi.Factory;
import com.lion.vip.api.spi.SpiLoader;

/**
 * 接口：消息推送工厂
 */
public interface MessagePusherFactory extends Factory<MessagePusher> {
    static MessagePusher create(){
        return SpiLoader.load(MessagePusherFactory.class).get();
    }
}
