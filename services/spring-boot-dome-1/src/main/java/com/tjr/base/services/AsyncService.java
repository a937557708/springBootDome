package com.tjr.base.services;


import com.tjr.rqconsumer.listener.DirectReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {
    private static final Logger logger=  LoggerFactory.getLogger(AsyncService.class);

    @Async
    public void test() {

        try {
            Thread.sleep(1000);
        }catch (Exception ex){

        }
        logger.info("test");
    }
}
