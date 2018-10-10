<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
	<head>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>
			<spring:message code="title.access.denied.view" text="Não autorizado" />
		</title>
	</head>
	<body>
		<h1><spring:message code="caption.access.denied" text="Acesso negado"/></h1>  
	  	<p><spring:message code="message.access.denied.contact.the.administrator" text="Entre em contato com o administrador para ter acesso à funcionalidade desejada."/></p>
	</body>
</html>