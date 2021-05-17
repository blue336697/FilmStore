package com.lhjitem.service;

import com.lhjitem.pojo.File;
import com.lhjitem.pojo.Page;

import java.util.List;

public interface FileService {

    public void addFile(File file);

    public void deleteFileById(Integer id);

    public void updateFile(File file);

    public File queryFileById(Integer id);

    public List<File> queryFiles();

    Page<File> page (int pageNo, int pageSize);

    Page<File> pageByPrice(int pageNo,int pageSize,int min,int max);
}
