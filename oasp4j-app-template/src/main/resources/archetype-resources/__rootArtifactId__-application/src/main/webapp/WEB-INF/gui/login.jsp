#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page import="org.springframework.security.web.csrf.CsrfToken" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Login Page</title>
</head>
<body onload='document.loginForm.j_username.focus();'/>

	<!-- DEBUG: ${symbol_dollar}{sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message} -->
	<c:if test="${symbol_dollar}{not empty error}">
		<div class="error">${symbol_dollar}{error}</div>
	</c:if>
	<c:if test="${symbol_dollar}{not empty msg}">
		<div class="msg">${symbol_dollar}{msg}</div>
	</c:if>


	<form name='loginForm'
		action="<c:url value='/j_spring_security_login' />" method='POST'>

		<table>
			<tr>
				<td>User:</td>
				<td><input type='text' name='j_username' /></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='j_password' /></td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
					value="submit" /></td>
			</tr>
		</table>
	</form>
</body>
</html>