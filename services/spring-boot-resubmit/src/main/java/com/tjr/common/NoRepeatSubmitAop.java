package com.tjr.common;

import com.tjr.base.annotation.NoRepeatSubmit;
import com.tjr.utils.EhcacheUtil;
import com.tjr.utils.IpUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.UUID;

@Aspect
@Component
public class NoRepeatSubmitAop {

    @Autowired
    private EhcacheUtil redisUtils;

    /**
     *     定义切入点
     */
    @Pointcut("@annotation(com.tjr.base.annotation.NoRepeatSubmit)")
    public void noRepeat() {}

    /**
     *     前置通知：在连接点之前执行的通知
     * @param point
     * @throws Throwable
     */
    @Before("noRepeat()")
    public void before(JoinPoint point) throws Exception{
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Assert.notNull(request, "request can not null");

        // 此处可以用token或者JSessionId
        String token = IpUtils.getIpAddress(request);
        String path = request.getServletPath();
        String key = getKey(token, path);
        String clientId = getClientId();
        Object lGet = redisUtils.get(key);
        // 获取注解
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        NoRepeatSubmit annotation = method.getAnnotation(NoRepeatSubmit.class);
        Long timeout = annotation.timeout();
        boolean isSuccess = false;
        if ( lGet == null) {

            isSuccess = redisUtils.set(key, clientId, timeout.intValue());
        }
        if (!isSuccess) {
            // 获取锁失败，认为是重复提交的请求
            redisUtils.set(key, clientId, timeout.intValue());
            throw new Exception("不可以重复提交");
        }

    }

    private String getKey(String token, String path) {
        return token + path;
    }

    private String getClientId() {
        return UUID.randomUUID().toString();
    }
}