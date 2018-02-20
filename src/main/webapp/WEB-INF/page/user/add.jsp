<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<title>添加用户</title>
</head>
<body>
	<form action="${www_url}/customer/add" method="post">
		 昵称姓名:<input type="text" name="nickName" /><br>
                    手机号:<input type="number" name="phoneNumber"><br>
         mail:<input type="text" name="mail"><br>
         passWord:<input type="password" name="passWord"><br>
		<input type="submit" value="提交" />
	</form>
</body>
</html>