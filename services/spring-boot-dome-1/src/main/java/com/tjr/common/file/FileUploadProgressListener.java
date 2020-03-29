package com.tjr.common.file;


import jodd.util.StringUtil;
import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 下载进度监听器
 */
@Component
public class FileUploadProgressListener implements ProgressListener {

    private HttpSession session;
    private String fileSessionId;
    public void setSession(HttpSession session,String fileSessionId) {
        this.session = session;
        this.fileSessionId=fileSessionId;
        if(StringUtil.isEmpty(fileSessionId)){
            this.fileSessionId="upload_percent"+(new Date().getTime());
        }
        session.setAttribute(this.fileSessionId, 0);
    }

    @Override
    public void update(long pBytesRead, long pContentLength, int pItems) {
        int percent = (int) (pBytesRead * 100.0 / pContentLength);
        session.setAttribute( this.fileSessionId, percent);

    }

}