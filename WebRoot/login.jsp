<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<link rel="stylesheet" href="css/login.css">
<link rel="shortcut icon" href="img/logo.ico" type="image/x-icon" />
</head>
<body>
	<section class="container">
		<div class="login">
			<h1>Login</h1>
			<a hidden="hidden" id="urlpath">${pageContext.request.contextPath}</a>
			${param.message}
			<form method="post"
				action="javascript:void(0);" id="loginform">
				<p>
					<input type="text" name="username" id="username" value=""
						placeholder="Username or Email">
				</p>
				<p>
					<input type="password" name="password" id="password" value=""
						placeholder="Password">
				</p>
				<p class="submit">
					<input type="reset" name="reset" id="reset" value="Reset">
					<input type="submit" name="commit" id="submit" value="Login"  onclick="login()">
				</p>
			</form>
		</div>
		<div class="login-help">
			<p class="remember_me">
				<input type="checkbox" name="remember_me" id="remember_me">
				Remember me on this computer
			</p>
			<br>
			<p>
				Forgot your password? <a href="index.jsp">Click here to reset it</a>.
			</p>
		</div>
	</section>
</body>
<!-- 为页面添加表单校验 -->
	<script type="text/javascript" src="http://libs.baidu.com/jquery/2.0.3/jquery.min.js"></script>
	<script type="text/javascript">


		function login(){
			var url=$("#urlpath").text()+"/servlet/LoginServlet";
			if(check()){
				//验证成功

				//发送请求
				$.ajax({
					url:url,
					type:'post',
					data:$("#loginform").serialize(),
					async: true, //默认为true 异步
					error:function(){
						alert('error');
					},
					success:function(data){
						//返回json数据
						var retJson=eval(data);

						//回送的信息
						console.log(retJson.state);
						var state=retJson.state;
						if(state=="0"){
							//登陆失败
							alert("登陆失败");
						}else if(state=="200"){
							//登陆成功,跳转到首页
							location.href=$("#urlpath").text()+"/index.jsp";

						}

					}
				});

			}
		}
		

		function check(){
			var flag = false;
			// 校验用户名
				var username = $("#username").val();
				var password = $("#password").val();
				if (username == "" || username.trim() == "") {
					alert("用户名不能为空！");
					return flag;
				} else if (password == "" || password.trim() == "") {
					alert("密码不能为空！");
					return flag;
				} else {
					flag = true;
					return flag;
				}

			return flag;
		}

	</script>
</html>