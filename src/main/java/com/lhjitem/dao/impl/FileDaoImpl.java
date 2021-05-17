package com.lhjitem.dao.impl;

import com.lhjitem.dao.FileDao;
import com.lhjitem.pojo.File;

import java.util.List;

public class FileDaoImpl extends BaseDao implements FileDao {

    @Override
    public int addFile(File file) {
        String sql = "INSERT INTO a_file(`name`,`author`,`price`,`sales`,`stock`,`img_path`)VALUES(?,?,?,?,?,?);";
        return update(sql,file.getName(),file.getAuthor(),file.getPrice(),file.getSales(),file.getStock(),file.getImgPath());

    }

    @Override
    public int deleteFileById(Integer id) {
        String sql = "delete from a_file where id = ?";
        return update(sql,id);
    }

    @Override
    public int updateFile(File file) {
        String sql = "update a_file set `name`=?,`author`=?,`price`=?,`sales`=?,`stock`=?,`img_path`=? where id=?";
        return update(sql,file.getName(),file.getAuthor(),file.getPrice(),file.getSales(),file.getStock(),file.getImgPath(),file.getId());

    }

    @Override
    public File queryFileById(Integer id) {
        String sql = "select `id`,`name`,`author`,`price`,`sales`,`stock`,`img_path` imgPath from a_file where id = ?";
        return queryForOne(File.class,sql,id);
    }

    @Override
    public List<File> queryFiles() {
        String sql = "select `id`,`name`,`author`,`price`,`sales`,`stock`,`img_path` imgPath from a_file ";
        return queryForList(File.class,sql);
    }

    @Override
    public Integer queryForPageTotalCount() {
        String sql = "select count(*) from a_file";
        Number count = (Number) queryForSingleValue(sql);
        return count.intValue();
    }

    @Override
    public List<File> queryForPageItems(int begin,int pageSize) {
        String sql = "select * from a_file limit ?,?";

        return queryForList(File.class,sql,begin,pageSize);
    }

    @Override
    public Integer queryForPageTotalCountByPrice(int min, int max) {
        String sql = "select count(*) from a_file where price between ? and ?";
        Number count = (Number) queryForSingleValue(sql,min,max);
        return count.intValue();
    }

    @Override
    public List<File> queryForPageItemsByPrice(int begin, int pageSize, int min, int max) {
        String sql = "select * from a_file where price between ? and ? order by price limit ?,?";
        return queryForList(File.class,sql,min,max,begin,pageSize);
    }
}
