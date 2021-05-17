<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--尼玛的，真就一个空格就不行呗
	<%@ page contentType="text/html;charset=UTF-8" language="java" %>
	<%@ page contentType="text/html; charset=UTF-8" language="java" %>
	这两个？？？？？
--%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>蓝光下载分享首页</title>
    <%--静态包含base标签、css样式、jQuery文件--%>
    <%@include file="/pages/common/head.jsp"%>
    <Script type="text/javascript">
        $(function (){
            $("button.addToCart").click(function (){
                /**
                 * 在事件响应的函数中this就表示正在响应的dom对象，先转换成jquery对象，然后调用attr方法（返回被选元素的属性值）
                 * @type {*|jQuery}
                 */
                var fileId = $(this).attr("fileId");
                //后面跟地址，指哪打哪
               // location.href = "http://localhost:8080/FileStore/cart?action=addItem&id=" + fileId;
                //更改为ajax，添加商品到购物车
                $.getJSON("http://localhost:8080/FileStore/cart","action=addItemAjax&id=" + fileId,function (data){
                    //将拿到的数据更新到页面对应位置
                    $("#totalCount").text("您的购物车里有"+ data.totalCount +"件商品");
                    $("#lastName").text("【"+data.lastName+"】");
                });
            });
        });
    </Script>
</head>
<body>

<div id="header">
    <img class="logo_img" alt="" src="static/img/index.jpg" >
    <span class="wel_word">BlueRayStore</span>
    <div>
    <%--如果用户还没有登录，显示 【登录、注册的菜单】--%>
    <c:if test="${empty sessionScope.user}">
        <a href="pages/user/login.jsp">登录</a>  |
        <a href="pages/user/regist.jsp">注册</a> &nbsp;
    </c:if>
    <%--如果已经登录，则显示登陆成功之后的用户信息--%>
    <c:if test="${not empty sessionScope.user}">
        <%@include file="/pages/common/login_success_menu.jsp"%>
    </c:if>
    <a href="pages/cart/cart.jsp">购物车</a>
    <a href="pages/manager/manager.jsp">后台管理</a>
    </div>
</div>
<div id="main">
    <div id="file">
        <div class="file_cond">
            <form action="client/file" method="get">
                <input type="hidden" name="action" value="pageByPrice">
                价格：<input id="min" type="text" name="min" value="${param.min}"> 元 -
                <input id="max" type="text" name="max" value="${param.max}"> 元
                <input type="submit" value="查询" />
            </form>

        </div>
        <div style="text-align: center">

            <c:if test="${empty sessionScope.cart.items}">
                <span id="totalCount"> </span>
                <div>
                    <span id="lastName" style="color: red">您的购物车中还未添置商品</span>
                </div>
            </c:if>

            <c:if test="${not empty sessionScope.cart.items}">
                <span id="totalCount"><%--您的购物车里有${sessionScope.cart.totalCount}件商品--%></span>
                <div>
                    您刚刚将<span style="color: red" id="lastName">${sessionScope.lastName}</span>加入到了购物车中
                </div>
            </c:if>
        </div>

        <c:forEach items="${requestScope.page.items}" var="file">
            <div class="b_list">
                <div class="img_div">
                    <img class="file_img" alt="" src="${file.imgPath}" />
                </div>
                <div class="file_info">
                    <div class="file_name">
                        <span class="sp1">影片:</span>
                        <span class="sp2">${file.name}</span>
                    </div>
                    <div class="file_author">
                        <span class="sp1">导演:</span>
                        <span class="sp2">${file.author}</span>
                    </div>
                    <div class="file_price">
                        <span class="sp1">价格:</span>
                        <span class="sp2">￥${file.price}</span>
                    </div>
                    <div class="file_sales">
                        <span class="sp1">销量:</span>
                        <span class="sp2">${file.sales}</span>
                    </div>
                    <div class="file_amount">
                        <span class="sp1">库存:</span>
                        <span class="sp2">${file.stock}</span>
                    </div>
                    <div class="file_add">
                        <button fileId="${file.id}" class="addToCart">加入购物车</button>
                    </div>
                </div>
            </div>
        </c:forEach>


    </div>
    <%--静态包含分页条--%>
    <%@include file="/pages/common/page_nav.jsp"%>

</div>

<div id="bottom">
		<span>
			<%@include file="/pages/common/footer.jsp"%>
		</span>
</div>
</body>
</html>