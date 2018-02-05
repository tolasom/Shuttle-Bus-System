package com.MainController;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SecurityController {

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView defaultPage() {

		ModelAndView model = new ModelAndView();
		
		model.setViewName("project");

		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	
		 UserDetails userDetails=(UserDetails) auth.getPrincipal();
		 
		 Map<String ,Object> role= new HashMap<String ,Object>();
		 role.put("role1",userDetails.getAuthorities());
		 Object role2=(Object) role.get("role1");
		 StringBuffer userRole=new StringBuffer(role2.toString());
		 System.out.print(userRole);
		 if ("[ROLE_ADMIN]".contentEquals(userRole))
		 {
			 model.setViewName("bus_management");
		 }
		 if ("[ROLE_N_ADMIN]".contentEquals(userRole))
		 {
			 model.setViewName("projectAdminView");
		 }
		 if ("[ROLE_USER]".contentEquals(userRole))
		 {
			 model.setViewName("projectUserView");
		 }
		
		return model;

	}

/*	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

		ModelAndView model = new ModelAndView();
		model.setViewName("projectAdminView");

		return model;

	}	*/

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("security/login");

		return model;

	}

	// customize the error message
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession().getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "Invalid username and password!";
		}

		return error;
	}
	

	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			
			model.addObject("username", userDetail.getUsername());

		}
		model.setViewName("security/403");
		return model;

	}

}
