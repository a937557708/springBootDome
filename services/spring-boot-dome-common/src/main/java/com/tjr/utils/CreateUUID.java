package com.tjr.utils;

import com.baidu.fsg.uid.UidGenerator;
import com.baidu.fsg.uid.impl.CachedUidGenerator;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * 生成UIID
 */
public class CreateUUID {
    /**
     * 百度uiid
     * @return
     */
    public static String getBaiDuUIID(){
        UidGenerator uidGenerator=new CachedUidGenerator();
        long uid = uidGenerator.getUID();
        return String.valueOf(uid);
    }
    public static String getUIID(){
        String uuid = UUID.randomUUID().toString();
        System.out.println("uuid : " + uuid);
        uuid = UUID.randomUUID().toString().replaceAll("-","");
        return uuid;
    }

}
