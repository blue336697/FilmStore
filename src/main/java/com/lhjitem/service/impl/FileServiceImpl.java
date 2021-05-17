package com.lhjitem.service.impl;

import com.lhjitem.dao.FileDao;
import com.lhjitem.dao.impl.FileDaoImpl;
import com.lhjitem.pojo.File;
import com.lhjitem.pojo.Page;
import com.lhjitem.service.FileService;

import java.util.List;

public class FileServiceImpl implements FileService {

    private FileDao fileDao = new FileDaoImpl();
    @Override
    public void addFile(File file) {
        fileDao.addFile(file);
    }

    @Override
    public void deleteFileById(Integer id) {
        fileDao.deleteFileById(id);
    }

    @Override
    public void updateFile(File file) {
        fileDao.updateFile(file);
    }

    @Override
    public File queryFileById(Integer id) {
        return fileDao.queryFileById(id);
    }

    @Override
    public List<File> queryFiles() {
        return fileDao.queryFiles();
    }

    @Override
    public Page<File> page(int pageNo, int pageSize) {
        Page<File> page = new Page<>();

        //设置每页显示的数量
        page.setPageSize(pageSize);
        //求总记录数
        Integer pageTotalCount = fileDao.queryForPageTotalCount();
        //设置总页面数
        page.setPageTotalCount(pageTotalCount);
        //求页码
        Integer pageTotal = pageTotalCount / pageSize;
        if(pageTotalCount % pageSize > 0){
            pageTotal+=1;
        }
        //设置总页码
        page.setPageTotal(pageTotal);


        //设置当前页码
        page.setPageNo(pageNo);
        //求当前页的开始索引
        int begin = (page.getPageNo() - 1) * pageSize;
        //求当前页面数据
        List<File> items = fileDao.queryForPageItems(begin,pageSize);
        //设置当前页面
        page.setItems(items);
        return page;
    }

    @Override
    public Page<File> pageByPrice(int pageNo, int pageSize, int min, int max) {
        Page<File> page = new Page<>();
        //设置每页显示的数量
        page.setPageSize(pageSize);
        //求总记录数
        Integer pageTotalCount = fileDao.queryForPageTotalCountByPrice(min,max);
        //设置总记录数
        page.setPageTotalCount(pageTotalCount);
        //求页码
        Integer pageTotal = pageTotalCount / pageSize;
        if(pageTotalCount % pageSize > 0){
            pageTotal+=1;
        }
        //设置页码
        page.setPageTotal(pageTotal);

        //设置当前页码
        page.setPageNo(pageNo);

        //求当前页的开始索引
        int begin = (page.getPageNo() - 1) * pageSize;
        //求当前页面数据
        List<File> items = fileDao.queryForPageItemsByPrice(begin,pageSize,min,max);
        //设置当前页面
        page.setItems(items);

        return page;
    }
}
