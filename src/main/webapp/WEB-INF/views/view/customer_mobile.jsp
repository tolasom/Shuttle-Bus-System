
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html><html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,shrink-to-fit=no">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <title>Shuttle Bus</title>
</head>
<body>
<jsp:include page='./payment.jsp'/>
<input type="hidden" id="csrfToken" value="${_csrf.token}"/>
<input type="hidden" id="csrfHeader" value="${_csrf.headerName}"/>
<script>
    window.token = document.getElementById("csrfToken").value
    window.headerName = document.getElementById("csrfHeader").value
    sessionStorage.setItem("path","/")
</script>
<form action="/sbs/logout" method="post" id="logoutForm">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
<noscript>You need to enable JavaScript to run this app.</noscript>
<div id="root"></div>
<script type="text/javascript" src="/resources/build/static/js/bundle.js"></script>
<!--<script type="text/javascript" src="/sbs/resources/build/static/js/compressed.js"></script>-->
<!--<script type="text/javascript" src="/resources/build/static/js/main.bccf8d00.js"></script>
<link rel="stylesheet" href="/resources/build/static/css/main.f1e2f445.css">-->
<!--<script type="text/javascript" src="http://localhost:3000/static/js/bundle.js"></script>-->
</body>

</html>
