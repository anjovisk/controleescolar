<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
	<head>
		<meta charset="UTF-8" />
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">		
		<title>
			<spring:message code="title.authentication.view" text="Autenticação" />
		</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
		<meta http-equiv="Content-Language" content="pt-br">
		
		<spring:url value="/resources/js/jquery-2.1.1.min.js" var="jqueryJs" />
		<script src="${jqueryJs}"></script>
						
		<spring:message code="placeholder.login" text="Insira o login ou e-mail" var="loginText"/>
		<spring:message code="placeholder.password" text="Insira a senha" var="passwordText"/>
	</head>
	<body>
		<form th:action="@{${pageContext.request.contextPath}/login}" method="post" novalidate>					
			<div>
				<label><spring:message code="label.user" text="Usuário:"/></label>
				<input name="username" placeholder="${loginText}" autocomplete="off" maxlength="45" autofocus="autofocus"/>							
				<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION.message}">
					<span><spring:message code="message.invalid.user.or.password" text="Usuário ou senha incorretos" /></span>
				</c:if>
			</div>
			<div>
				<div>
					<label><spring:message code="label.password:" text="Senha:"/></label>
					<input type="password" name="password" placeholder="${passwordText}" maxlength="12" />
				</div>
			</div>	
			<div>				
				<button type="submit">
					<spring:message code="caption.enter" text="Entrar"/>
				</button>
			</div>
		</form>	
	</body>
</html>