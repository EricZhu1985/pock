<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script>
function login() {
	
	$.ajax({
		type: 'post',
		url: 'login',
		data: {
			'pwd' : $('#pwd').val(),
			'name' : $('#name').val()
		},
		dataType: 'json',
		success: function (result) {
			var ret = eval('('+result+')');
			if(ret) {
				document.location = "index.jsp";
			}
		}
	});

}
</script>
</head>
<body>
<%= request.getAttribute("errorMsg") %></br>
用户名: <input id="name" value=""><br/>
密码: <input id="pwd" type="password"><br/>
<input type="button" value="确认" onclick="login()"/>
</body>
</html>