<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>影片管理</title>
	<%--静态包含base标签、css样式、jQuery文件--%>
	<%@include file="/pages/common/head.jsp"%>
	<script type="text/javascript">
		$(function (){
			//给删除绑定单击确认事件
			$("a.deleteClass").click(function (){
				//confirm是确认提示框函数，返回true(true是默认，所以我们要修改默认情况)表示点击了，返回false表示点击取消
				//获取要删除的名称，可以通过此this（默认正在响应的dom对象）的父标签再重新定位到name
				return confirm("你确认要删除《"+$(this).parent().parent().find("td:first").text() +"》?");

				// return false;//阻止默认行为
			});
		});
	</script>
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="../../static/img/index.jpg" >
			<span class="wel_word">影片管理系统</span>
		<%--静态页面保存后台信息页面--%>
		<%@include file="/pages/common/manager_menu.jsp"%>
	</div>
	
	<div id="main">
		<table>
			<tr>
				<td>名称</td>
				<td>导演</td>
				<td>价格</td>
				<td>销量</td>
				<td>库存</td>
				<td colspan="2">操作</td>
			</tr>

			<c:forEach items="${requestScope.page.items}" var="file">
			<tr>
				<td>${file.name}</td>
				<td>${file.author}</td>
				<td>${file.price}</td>
				<td>${file.sales}</td>
				<td>${file.stock}</td>
				<td><a href="manager/file?action=getFile&id=${file.id}&pageNo=${requestScope.page.pageNo}">修改</a></td>
				<td><a class="deleteClass" href="manager/file?action=delete&id=${file.id}&pageNo=${requestScope.page.pageNo}">删除</a></td>
			</tr>
			</c:forEach>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td><a href="pages/manager/file_edit.jsp?pageNo=${requestScope.page.pageTotal}">添加影片</a></td>
			</tr>	
		</table>
		<%--静态包含分页条--%>
		<%@include file="/pages/common/page_nav.jsp"%>

	</div>

	<%--静态页面显示页脚信息--%>
	<%@include file="/pages/common/footer.jsp"%>
</body>
</html>