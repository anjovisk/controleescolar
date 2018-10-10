<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
	<spring:url value="/resources/js/jquery-2.1.1.min.js" var="jqueryJs" />
	<script src="${jqueryJs}"></script>
</head>
<body>
	<div id="centralized-content-div">
		<jsp:include page="teste-registration-list-view.jsp"/>
	</div>
</body>
<script type="text/javascript">
	function showCentralizedContent(data, message) {
		$('#centralized-content-div').html(data);
		if (message) {
			alert(message);
		}
	}
	
	function showTesteList() {
		var url = '${pageContext.request.contextPath}/app/controleescolar/area/teste/testeRegistrationController/list?loadMainView=false';
		$.ajax({
			cache: false,
			type: "GET",
			url: url,
			success: function(data) {
				showCentralizedContent(data);
			},
			statusCode: {
			    401: function() {
			    	location.reload();
			    }
			}
		});
	}
	
	function showTesteRegistration(id) {
		var url = '${pageContext.request.contextPath}/app/controleescolar/area/teste/testeRegistrationController/edit';
		if (id) {
			url = url + "?id=" + id;
		}
		$.ajax({
			cache: false,
			type: "GET",
			url: url,
			success: function(data) {
				showCentralizedContent(data);
			},
			statusCode: {
			    401: function() {
			    	location.reload();
			    }
			}
		});
	}
	
	function deleteTeste(id) {
		var url = '${pageContext.request.contextPath}/app/controleescolar/area/teste/testeRegistrationController/delete';
		if (id) {
			url = url + "?ids=" + id;
		}
		$.ajax({
			cache: false,
			type: "POST",
			url: url,
			success: function(data) {
				var success = (data["success"]);
				if (success) {
					showTesteList();
				} else {
					alert("Não foi possível apagar o registro.");
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
</html>
