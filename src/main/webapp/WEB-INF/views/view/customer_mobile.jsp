
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html><html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,shrink-to-fit=no">
    <meta name="theme-color" content="#000000"><link rel="manifest" href="/sbs/resources/build/manifest.json">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="shortcut icon" href="/sbs/resources/build/favicon.ico">
    <title>React App</title>
</head>
<body>
<input type="hidden" id="csrfToken" value="${_csrf.token}"/>
<input type="hidden" id="csrfHeader" value="${_csrf.headerName}"/>
<script>
    window.token = document.getElementById("csrfToken").value
    window.headerName = document.getElementById("csrfHeader").value
</script>
<form action="/sbs/logout" method="post" id="logoutForm">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
<noscript>You need to enable JavaScript to run this app.</noscript>
<div id="root"></div>
<!--<script type="text/javascript" src="/sbs/resources/build/static/js/main.72bd9adf.js">
</script>
<link rel="stylesheet" href="/sbs/resources/build/static/css/main.0c03517c.css">-->
<script type="text/javascript" src="http://10.10.1.21:3000/static/js/bundle.js"></script>
</body>

</html>