<%--
  Created by IntelliJ IDEA.
  User: zzzzzzzzzz
  Date: 2021/5/7
  Time: 23:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--写base路径，永远固定到相对路径跳转的结果，加上以后，对于各种页面的路径都要参考于这个路径来改-->
<%--这里base路径要写成动态的，因为别人访问这个时，本地的localhost并没有这些css样式，jQuery等等的一些数据--%>
<%
    String basePath = request.getScheme()
            +"://"
            +request.getServerName()
            +":"
            +request.getServerPort()
            +request.getContextPath()
            +"/";

    pageContext.setAttribute("basePath",basePath);
%>

<base href="<%=basePath%>">
<link type="text/css" rel="stylesheet" href="static/css/style.css" >
<script type="text/javascript" src="static/script/jquery-1.7.2.js"></script>
