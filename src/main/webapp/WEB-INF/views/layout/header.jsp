<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@page session="true"%>
<c:url value="/logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                
                <sec:authorize access="hasRole('ROLE_USER')">
                <a class="navbar-brand" href="projectUserView" style="color:#fff;">KIT POINT SYSTEM</a>
                </sec:authorize>  
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                <a class="navbar-brand" href="project" style="color:#fff;">KIT POINT SYSTEM</a>
                </sec:authorize>  
                <sec:authorize access="hasRole('ROLE_N_ADMIN')">
                <a class="navbar-brand" href="projectAdminView" style="color:#fff;">KIT POINT SYSTEM</a>
                </sec:authorize> 
            </div>
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="color:#fff;"><i class="fa fa-user"></i> ${pageContext.request.userPrincipal.name}<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="profileMenu"><i class="fa fa-fw fa-user"></i> Profile</a>
                        </li>

                        <li class="divider"></li>
                        <li>
                            <a href="javascript:formSubmit()"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                        </li>
                    </ul>
                </li>
            </ul>