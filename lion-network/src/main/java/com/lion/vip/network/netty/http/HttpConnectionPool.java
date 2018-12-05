package com.lion.vip.network.netty.http;

import com.google.common.collect.ArrayListMultimap;
import com.lion.vip.tools.config.CC;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;


/**
 * http 连接池
 */
public class HttpConnectionPool {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpConnectionPool.class);

    private static final int maxConnPerHost = CC.lion.http.max_conn_per_host;    //每台机器最大允许的连接数
    private final AttributeKey<String> hostKey = AttributeKey.newInstance("host");
    private final ArrayListMultimap<String, Channel> channelPool = ArrayListMultimap.create();

    public synchronized Channel tryAcquire(String host) {
        List<Channel> channelList = channelPool.get(host);
        if (channelList == null || channelList.isEmpty()) {
            return null;
        }
        Iterator<Channel> iterator = channelList.iterator();
        while (iterator.hasNext()) {
            Channel channel = iterator.next();
            iterator.remove();
            if (channel.isActive()) {    //如果通道是可用的，再进行后续操作
                LOGGER.debug("tryAcquire channel success, host={}", host);
                channel.attr(hostKey).set(host);
                return channel;
            } else {    //否则，可能原因是链接意外不可用了，比如：keepAlive_timeout
                LOGGER.warn("tryAcquire channel false channel is inactive, host={}", host);
            }
        }
        return null;
    }

    public synchronized void tryRelease(Channel channel) {
        String host = channel.attr(hostKey).getAndSet(null);
        List<Channel> channels = channelPool.get(host);
        if (channels == null || channels.size() < maxConnPerHost) {
            LOGGER.debug("tryRelease channel success, host={}", host);
            channelPool.put(host, channel);
        } else {
            LOGGER.debug("tryRelease channel pool size over limit={}, host={}, channel closed.", maxConnPerHost, host);
            channel.close();
        }
    }

    public void attachHost(String host, Channel channel) {
        channel.attr(hostKey).set(host);
    }

    public void close() {
        channelPool.values().forEach(Channel::close);
        channelPool.clear();
    }

}
