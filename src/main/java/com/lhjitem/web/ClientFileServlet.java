package com.lhjitem.web;

import com.lhjitem.pojo.File;
import com.lhjitem.pojo.Page;
import com.lhjitem.service.FileService;
import com.lhjitem.service.impl.FileServiceImpl;
import com.lhjitem.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClientFileServlet extends BaseServlet{
    private FileService fileService = new FileServiceImpl();

    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求参数pageNo，pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        //2.调用fileService.page（pageNo，pageSize）,Page对象
        Page<File> page = fileService.page(pageNo,pageSize);

        page.setUrl("client/file?action=page");
        //3.保存到request域中
        req.setAttribute("page",page);
        //4.请求转发到 /pages/manager/file_manager.jsp页面中
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);
    }


    protected void pageByPrice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求参数pageNo,pageSize,min,max
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        int min = WebUtils.parseInt(req.getParameter("min"), 0);
        int max = WebUtils.parseInt(req.getParameter("max"), 9999);

        //2.调用fileService.pageByPrice(pageNo,pageSize,min,max) page对象
        Page<File> page = fileService.pageByPrice(pageNo, pageSize, min, max);

        //为了解决查询完分页跳转问题（点分页又回到未查询的页面的情况）
        StringBuilder sb = new StringBuilder("client/file?action=pageByPrice");
        //如果有最小价格的参数，追加到分页条的地址参数中去
        if(req.getParameter("min") != null){
            sb.append("&min=").append(req.getParameter("min"));
        }

        //如果有最价格的参数，追加到分页条的地址参数中去
        if(req.getParameter("max") != null){
            sb.append("&max=").append(req.getParameter("max"));
        }

        page.setUrl(sb.toString());
        //3.保存分页对象到request域中
        req.setAttribute("pageByPrice",page);
        //4.请求转发到/pages/client/index.jsp页面
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);
    }
}
