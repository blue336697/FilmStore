package com.lhjitem.web;

import com.lhjitem.pojo.File;
import com.lhjitem.pojo.Page;
import com.lhjitem.service.FileService;
import com.lhjitem.service.impl.FileServiceImpl;
import com.lhjitem.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FileServlet extends BaseServlet {

    private FileService fileService = new FileServiceImpl();



    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的参数，封装成为file对象
        File file = WebUtils.copyParamToBean(req.getParameterMap(),new File());
        //2.调用fileService的add方法，对数据库进行添加
        fileService.addFile(file);
        //3.跳回影片列表页面,这里不能使用请求转发（只有一次请求），因为在页面加载后最后一次请求还是会回到请求转发的请求上进行重复添加
        //  所以要用重定向（两次请求）
        req.getRequestDispatcher("./pages/manager/file_manager.jsp").forward(req,resp);
//        resp.sendRedirect(req.getContextPath() + "/manager/file?action=page&pageNo=" + req.getParameter("pageNo"));//这个不知道为啥行不通咱们直接用绝对路径，也不做判断了
//        resp.sendRedirect(req.getContextPath() + "/manager/file_manager.jsp");

    }

    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1获取请求的参数id，影片编程
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        //2.调用fileService.deleteBookById()；删除影片
        fileService.deleteFileById(id);
        //3.重定向回到影片列表看删除情况
        resp.sendRedirect(req.getContextPath() + "/manager/file?action=page&pageNo=" + req.getParameter("pageNo"));
    }

    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //这个方法为了更新修改的影片的信息
        //1.获取请求的参数，封装成为bean对象
        File file = WebUtils.copyParamToBean(req.getParameterMap(),new File());
        //2.调用FileService.updateFile()方法修改影片
        fileService.updateFile(file);
        //3.重定向到图书管理列表页面,刷新页面数据
        resp.sendRedirect(req.getContextPath() + "/manager/file?action=page&pageNo=" + req.getParameter("pageNo"));
    }

    protected void getFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //这个方法就是为了获取修改影片的信息
        //1.获取请求的影片编号
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        //2.调用fileService.queryFileById查询影片
        File file = fileService.queryFileById(id);
        //3.保存到影片的request域中去
        req.setAttribute("file",file);
        //4.请求转发到 pages/manager/file_edit.jsp页面
        req.getRequestDispatcher("/pages/manager/file_edit.jsp").forward(req,resp);
    }

    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.通过FileService查询全部数据
        List<File> files = fileService.queryFiles();

        //2.把数据全部保存到request域中
        req.setAttribute("files",files);

        //3.请求转发到/pages/manager/file_manager.jsp中
        req.getRequestDispatcher("/pages/manager/file_manager.jsp").forward(req,resp);
    }


    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求参数pageNo，pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        //2.调用fileService.page（pageNo，pageSize）,Page对象
        Page<File> page = fileService.page(pageNo,pageSize);

        page.setUrl("manager/file?action=page");
        //3.保存到request域中
        req.setAttribute("page",page);
        //4.请求转发到 /pages/manager/file_manager.jsp页面中
        req.getRequestDispatcher("/pages/manager/file_manager.jsp").forward(req,resp);
    }
}
