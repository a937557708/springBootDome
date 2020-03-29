package com.tjr.fileproess.service.impl;

import com.tjr.fileproess.dao.FileInfoRepository;
import com.tjr.fileproess.model.FileInfo;
import com.tjr.fileproess.service.FileInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author luoliang
 * @date 2018/6/20
 */
@Service
public class FileInfoServiceImpl implements FileInfoService {
    @Resource
    private FileInfoRepository fileInfoRepository;

    @Override
    public FileInfo addFileInfo(FileInfo fileInfo) {
        return fileInfoRepository.save(fileInfo);
    }
}
