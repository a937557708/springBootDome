package com.tjr.utils;



import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * EhCache 缓存工具类
 * 使用String格式的value
 * <p>
 * {@link com.spz.demo.singleboot.config.EhcacheConfig}
 * {@link CacheConst}
 *
 * @author zp
 */
@Component
public class EhcacheUtil {


    @Autowired
    @Lazy
    private CacheManager cacheManager;;



    // 默认的缓存存在时间（秒）
    private static final int DEFAULT_LIVE_SECOND = 20 * 60;

    /**
     * 添加缓存
     *
     * @param key
     * @param value
     * @param timeToLiveSeconds 缓存生存时间（秒）
     */
    public Boolean set(String key, String value, int timeToLiveSeconds) {
        boolean result = false;
        try {
            Cache cache = cacheManager.getCache(CacheConst.PROJECT.getName());
            Element element = new Element(
                    key, value,
                    0,// timeToIdleSeconds=0
                    timeToLiveSeconds);
            cache.put(element);
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 添加缓存
     * 使用默认生存时间 {@link DEFAULT_LIVE_SECOND}
     *
     * @param key
     * @param value
     */
    public Boolean set(String key, String value) {
        boolean result = false;
        try {
            Cache cache = cacheManager.getCache(CacheConst.PROJECT.getName());
            Element element = new Element(
                    key, value,
                    0,// timeToIdleSeconds
                    DEFAULT_LIVE_SECOND);
            cache.put(element);
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 添加缓存
     *
     * @param key
     * @param value
     * @param timeToIdleSeconds 对象空闲时间，指对象在多长时间没有被访问就会失效。
     *                          只对eternal为false的有效。传入0，表示一直可以访问。以秒为单位。
     * @param timeToLiveSeconds 缓存生存时间（秒）
     *                          只对eternal为false的有效
     */
    public boolean set(String key, String value, int timeToIdleSeconds, int timeToLiveSeconds) {
        boolean result = false;
        try {
            Cache cache = cacheManager.getCache(CacheConst.PROJECT.getName());
            Element element = new Element(
                    key, value,
                    timeToIdleSeconds,
                    timeToLiveSeconds);
            cache.put(element);
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    public String get(String key) {
        Cache cache = cacheManager.getCache(CacheConst.PROJECT.getName());
        if(cache==null){
            return null;
        }
        Element element = cache.get(key);
        if (element == null) {
            return null;
        }
        return (String) element.getObjectValue();
    }

}
