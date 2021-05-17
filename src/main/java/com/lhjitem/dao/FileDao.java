package com.lhjitem.dao;

import com.lhjitem.pojo.File;

import java.util.List;

public interface FileDao {

    public int addFile(File file);

    public int deleteFileById(Integer id);

    public int updateFile(File file);

    public File queryFileById(Integer id);

    public List<File> queryFiles();

    public Integer queryForPageTotalCount();

    public List<File> queryForPageItems(int begin,int pageSize);

    public Integer queryForPageTotalCountByPrice(int min,int max);

    public List<File> queryForPageItemsByPrice(int begin,int pageSize,int min,int max);
}
