<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div>
	<form:form id="testeForm" method="post" modelAttribute="entityForm">
		<form:hidden path="code"/>
		<form:input path="name"/>
	</form:form>
	<button type="button" onclick="save();">Salvar</button>
	<button type="button" onclick="showTesteList();">Cancelar</button>
	<script type="text/javascript">
		function save() {
			var url = '${pageContext.request.contextPath}/app/controleescolar/area/teste/testeRegistrationController/save';
			$.ajax({
				cache: false,
				type: "POST",
				url: url,
				data: $("#testeForm").serialize(),
				success: function(data) {
					var errors = data["errors"];
					if (errors) {
						alert("Ocorreu algum erro ao tentar salvar o registro.");
					} else {
						showTesteList();
					}
				},
				statusCode: {
				    401: function() {
				    	location.reload();
				    }
				}
			});
		}
	</script>
</div>
