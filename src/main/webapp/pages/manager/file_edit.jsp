<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>编辑影片</title>
	<%--静态包含base标签、css样式、jQuery文件--%>
	<%@include file="/pages/common/head.jsp"%>
<style type="text/css">
	h1 {
		text-align: center;
		margin-top: 200px;
	}
	
	h1 a {
		color:red;
	}
	
	input {
		text-align: center;
	}
</style>
</head>
<body>
		<div id="header">
			<img class="logo_img" alt="" src="../../static/img/index.jpg" >
			<span class="wel_word">编辑影片</span>
			<%--静态页面保存后台信息页面--%>
			<%@include file="/pages/common/manager_menu.jsp"%>
		</div>
		
		<div id="main">
			<form action="manager/file" method="get">
				<input type="hidden" name="pageNo" value="${param.pageNo}">
				<%--这种方案就是采用查看是否有id这个值跟着页面一起传入，事实就是只有更新是才会传入，所以当有id这个返回false，自然就更新了--%>
				<input type="hidden" name="action" value="${ empty param.id ? "add":"update"}"/>
					<input type="hidden" name="id" value="${ requestScope.file.id}"/>
				<table>
					<tr>
						<td>名称</td>
						<td>导演</td>
						<td>价格</td>
						<td>销量</td>
						<td>库存</td>
						<td colspan="2">操作</td>
					</tr>		
					<tr>
						<td><input name="name" type="text" value="${requestScope.file.name}"/></td>
						<td><input name="author" type="text" value="${requestScope.file.author}"/></td>
						<td><input name="price" type="text" value="${requestScope.file.price}"/></td>
						<td><input name="sales" type="text" value="${requestScope.file.sales}"/></td>
						<td><input name="stock" type="text" value="${requestScope.file.stock}"/></td>
						<td><input type="submit" value="提交"/></td>
					</tr>	
				</table>
			</form>
			
	
		</div>

		<%--静态页面显示页脚信息--%>
		<%@include file="/pages/common/footer.jsp"%>
</body>
</html>