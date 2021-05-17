<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我的订单</title>
	<%--静态包含base标签、css样式、jQuery文件--%>
	<%@include file="/pages/common/head.jsp"%>
<style type="text/css">
	h1 {
		text-align: center;
		margin-top: 200px;
	}
</style>
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="../../static/img/index.jpg" >
			<span class="wel_word">我的订单</span>
		<%--静态包含登录成功的显示信息--%>
			<%@include file="/pages/common/login_success_menu.jsp"%>
	</div>
	
	<div id="main">
		
		<table>
			<tr>
				<td>日期</td>
				<td>金额</td>
				<td>状态</td>
				<td>详情</td>
			</tr>

			<%--<c:if test="${empty sessionScope.cart.items}">
				&lt;%&ndash;购物车为空的情况&ndash;%&gt;
				<tr>
					<td colspan="5"><a href="index.jsp">亲，当前购物车为空，请浏览影片添加商品噢！</a></td>
				</tr>
			</c:if>

			<c:if test="${not empty requestScope.cart.items}">
				&lt;%&ndash;购物车不为空的情况&ndash;%&gt;
				<c:forEach items="${sessionScope.cart.items}" var="entry">
					&lt;%&ndash;我们使用session域中的数据，可以不经过servlet获取这些影片的信息,这里遍历的是一个map集合&ndash;%&gt;
					<tr>
						<td>${entry.value.name}</td>
						<td>
							<input class="updateCount" style="width: 60px"
								   fileId="${entry.value.id}" type="text" value="${entry.value.count}">
						</td>
						<td>${entry.value.price}</td>
						<td>${entry.value.totalPrice}</td>
						<td><a class="deleteItem" href="cart?action=deleteItem&id=${entry.value.id}">删除</a></td>


					</tr>
				</c:forEach>
			</c:if>--%>
			<td>${entry}</td>
			<td>190.00</td>
			<td>已完成</td>
			<td><a href="#">查看详情</a></td>


		</table>
		
	
	</div>

	<%--静态页面显示页脚信息--%>
	<%@include file="/pages/common/footer.jsp"%>
</body>
</html>