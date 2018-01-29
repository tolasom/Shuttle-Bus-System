<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<div class="collapse navbar-collapse navbar-ex1-collapse" id="menu">
                <ul class="nav navbar-nav side-nav">
                     <sec:authorize access="hasRole('ROLE_ADMIN')">
	                    <li id="projectStlye">
	                        <a href="project" ><i class="fa fa-fw fa-clipboard fa-3x">
	                        </i><span>&nbsp;Project</span> </a>
	                    </li>
	                    <li id="taskStlye">
	                        <a href="task"><i class="fa fa-fw fa-file fa-3x"></i>&nbsp;&nbsp;Tasks</a>
	                    </li>
	                    <li id="reportingStyle">
	                        <a href="reporting"><i class="fa fa-fw fa-bar-chart-o fa-3x"></i>&nbsp;Report</a>
	                    </li>
	                   
	                    <li id="settingStlye">
	                        <a href="setting"><i class="fa fa-fw fa-wrench fa-3x"></i>&nbsp;Setting</a>
	                    </li>
            		</sec:authorize>
            		 <sec:authorize access="hasRole('ROLE_N_ADMIN')">
	                    <li id="projectStlye">
	                        <a href="projectAdminView" ><i class="fa fa-fw fa-clipboard fa-3x">
	                        </i><span>&nbsp;Project</span> </a>
	                    </li>
	                    <li id="taskStlye">
	                        <a href="taskAdminView"><i class="fa fa-fw fa-file fa-3x"></i>&nbsp;&nbsp;Tasks</a>
	                    </li>
            		</sec:authorize>
            		 <sec:authorize access="hasRole('ROLE_USER')">
	                    <li id="projectStlye">
	                        <a href="projectUserView" ><i class="fa fa-fw fa-clipboard fa-3x">
	                        </i><span>&nbsp;Project</span> </a>
	                    </li>
	                    <li id="taskStlye">
	                        <a href="taskUserView"><i class="fa fa-fw fa-file fa-3x"></i>&nbsp;&nbsp;Tasks</a>
	                    </li>
	                    
            		</sec:authorize>
                </ul>
</div>