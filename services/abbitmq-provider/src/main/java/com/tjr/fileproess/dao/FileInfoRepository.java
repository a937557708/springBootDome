package com.tjr.fileproess.dao;

import com.tjr.fileproess.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author luoliang
 * @date 2018/6/20
 */
public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
}
