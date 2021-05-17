<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>购物车</title>
	<%--静态包含base标签、css样式、jQuery文件--%>
	<%@include file="/pages/common/head.jsp"%>

</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="static/img/index.jpg" >
			<span class="wel_word">购物车</span>
		<%@include file="/pages/common/login_success_menu.jsp"%>
	</div>
	<script type="text/javascript">
		$(function (){
			//给删除绑定单击事件
			$("a.deleteItem").click(function (){
				return confirm("你确定要删除【"+ $(this).parent().parent().find("td:first").text()+"】吗？")
			});


			//给清空购物车绑定单击事件
			$("a.clear").click(function (){
				return confirm("你确定要清空购物车吗？")
			});

			//给修改数量输入框绑定失去焦点事件（可用内容变化了在响应的change，帮我们省去了判断内容是否发生改变的代码）
			$(".updateCount").change(function (){
				var name = $(this).parent().parent().find("td:first").text();
				var id = $(this).attr("fileId");
				var count = this.value;
				if(confirm("确认要将【"+ name +"】数量改为"+ count + "吗？")){
					//默认为确认按钮，所以将修改信息发送给服务器
					location.href = "http://localhost:8080/FileStore/cart?action=updateCount&count="+count+"&id="+id;
				}else {
					//其他情况为取消即恢复原来的数量(defaultValue是表单项dom对象的属性，表示默认value值)
					this.value = this.defaultValue;
				}
			});
		});





	</script>
	<div id="main">
	
		<table>
			<tr>
				<td>商品名称</td>
				<td>数量</td>
				<td>单价</td>
				<td>金额</td>
				<td>操作</td>
			</tr>		

			<c:if test="${empty sessionScope.cart.items}">
				<%--购物车为空的情况--%>
				<tr>
					<td colspan="5"><a href="index.jsp">亲，当前购物车为空，请浏览影片添加商品噢！</a></td>
				</tr>
			</c:if>

			<c:if test="${not empty sessionScope.cart.items}">
				<%--购物车不为空的情况--%>
				<c:forEach items="${sessionScope.cart.items}" var="entry">
					<%--我们使用session域中的数据，可以不经过servlet获取这些影片的信息,这里遍历的是一个map集合--%>
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
			</c:if>

		</table>
		<%--如果非空才输出--%>
		<c:if test="${not empty sessionScope.cart.items}">
			<div class="cart_info">
				<span class="cart_span">购物车中共有<span class="b_count">${sessionScope.cart.totalCount}</span>件商品</span>
				<span class="cart_span">总金额<span class="b_price">${sessionScope.cart.totalPrice}</span>元</span>
				<span class="cart_span"><a class="clear" href="cart?action=clear">清空购物车</a></span>
				<span class="cart_span"><a href="order?action=createOrder">去结账</a></span>
			</div>
		</c:if>
	
	</div>

	<%--静态页面显示页脚信息--%>
	<%@include file="/pages/common/footer.jsp"%>
</body>
</html>