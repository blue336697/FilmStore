<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BDS会员注册页面</title>
	<%--静态包含base标签、css样式、jQuery文件--%>
<%@include file="/pages/common/head.jsp"%>
	<script type="text/javascript">
		$(function (){

			//给用户名绑定失去焦点事件，使用ajax来异步验证数据库是否有相同的用户名，实时的反馈给用户
			$("#username").blur(function (){
				//1.获取用户名
				var username = this.value;
				//2.发起ajax请求,data这个参数就是服务器json字符串传回的所有数据的总和
				$.getJSON("http://localhost:8080/FileStore/user","action=ajaxExistsUsername&username="+username,function (data){
					if(data.existsUsername){
						$("span.errorMsg").text("用户名已存在!");
					}else {
						$("span.errorMsg").text("用户名可用!");
					}
				});
			});


			//给验证码绑定单击事件，使其能点击切换验证码
			$("#code_img").click(function (){
				//在事件响应的function函数中有一个this对象，这个this对象，是当前正在响应的事件dom对象
				//src表示谷歌验证码img标签的图片路径，可读可写！！！，所以下面就是进行重新的调用赋值
				// 但火狐和ie浏览器都不行，点完一次就不能在点，因为浏览器为了加快进网页的速度就实现缓存一个，使之以后加载资源对比路径都是一个
				//解决方法就是加个时间值(时间戳)，这样每次浏览器就不会调用缓存的了，因为每次对比的路径都不一样了
				this.src = "${basePath}/kaptcha.jpg?d=" + new Date();
			});

			//给注册绑定单击事件
			$("#sub_btn").click(function (){
				//验证用户名
				var usernameObj = $("#username").val();

				var usernamePatt = /^\w{5,12}$/;//使用正则表达式

				if(!usernamePatt.test(usernameObj)){//test方法验证,不匹配时给用户反馈消息
					$("span.errorMsg").text("用户名不合法!");
					return false;//防止span方法的后续触发
				}

				//验证密码
				var passwordObj = $("#password").val();

				var passwordPatt = /^\w{5,12}$/;//使用正则表达式

				if(!passwordPatt.test(passwordObj)){//test方法验证,不匹配时给用户反馈消息
					$("span.errorMsg").text("密码不合法!");
					return false;//防止span方法的后续触发
				}

				//二次验证密码
				var repwdObj = $("#repwd").val();

				if(repwdObj != passwordObj){//test方法验证,不匹配时给用户反馈消息
					$("span.errorMsg").text("两次密码不相同");
					return false;//防止span方法的后续触发
				}
				
				
				//邮箱验证
				var emailObj = $("#email").val();
				var emailPatt = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;
				if(!emailPatt.test(emailObj)){
					$("span.errorMsg").text("邮箱不合法!");
					return false;//防止span方法的后续触发
				}

				//验证码
				var codeObj = $("#code").val();
				//优化代码的健壮性
				//检验验证码的内容：1.去掉验证码前后空格
				codeObj = $.trim(codeObj);
				if(codeObj == null || codeObj == ""){
					$("span.errorMsg").text("请输入验证码");
					return false;//防止span方法的后续触发
				}
			});



			$("span.errorMsg").text("");//其他情况不显示

		});


	</script>
<style type="text/css">
	.login_form{
		height:420px;
		margin-top: 25px;
	}

</style>
</head>
<body>
		<div id="login_header">
			<img class="logo_img" alt="" src="static/img/index.jpg" >
		</div>
		
			<div class="login_banner">
			
				<div id="l_content">
					<span class="login_word">欢迎注册</span>
				</div>
				
				<div id="content">
					<div class="login_form">
						<div class="login_box">
							<div class="tit">
								<h1>注册BluRay会员</h1>
								<span class="errorMsg">
<%--									<%=request.getAttribute("msg")==null?"":request.getAttribute("msg")%>--%>
									${requestScope.msg}
								</span>
							</div>
							<div class="form">
								<form action="user" method="post">
									<input type="hidden" name="action" value="regist"/>
									<label>用户名称：</label>
									<input class="itxt" type="text" placeholder="请输入用户名"
										   <%--value="<%=request.getAttribute("username")==null?"":request.getAttribute("username")%>"--%>
										   value="${requestScope.username}"
										   autocomplete="off" tabindex="1" name="username" id="username" />
									<br />
									<br />
									<label>用户密码：</label>
									<input class="itxt" type="password" placeholder="请输入密码"
										   autocomplete="off" tabindex="1" name="password" id="password" />
									<br />
									<br />
									<label>确认密码：</label>
									<input class="itxt" type="password" placeholder="确认密码"
										   autocomplete="off" tabindex="1" name="repwd" id="repwd" />
									<br />
									<br />
									<label>电子邮件：</label>
									<input class="itxt" type="text" placeholder="请输入邮箱地址"
<%--										   value="<%=request.getAttribute("email")==null?"":request.getAttribute("email")%>"--%>
										   value="${requestScope.email}"
										   autocomplete="off" tabindex="1" name="email" id="email" />
									<br />
									<br />
									<label>验证码：</label>
									<input class="itxt" type="text" name="code" style="width: 80px;" id="code"/>
									<img id="code_img" alt="" src="kaptcha.jpg" style="float: right; margin-right: 40px;width: 100px;height: 28px">
									<br />
									<br />
									<input type="submit" value="注册" id="sub_btn" />
									
								</form>
							</div>
							
						</div>
					</div>
				</div>
			</div>
		<%--静态页面显示页脚信息--%>
		<%@include file="/pages/common/footer.jsp"%>
</body>
</html>