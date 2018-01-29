package com.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	

	private static final String[] SUPER_ADMIN_MATCHERS = {		
		
		"/project.**",
		"/projectDetail.**",
		"/updateProjectDetail.**",
		"/additional_hour.**",
		"/updatePointMember.**",
		"/task.**",
		"/taskDetail.**",
		"/updateTaskDetail.**",
		"/reporting.**",
		"/projectReporting.**",
		"/skillSetReporting.**",
		"/taskReporting.**",
		"/setting.**",
		"/newUser.**",
		"/batch.**",
		"/createBatch.**",
		"/showUpdateBatch.**",
		"/kitpoint.**",
		"/view_update_point.**",
		"/viewPoint.**",
		"/updateAllPoint.**",
		"/kitpoint_value.**",
		"/projectCategory.**",
		"/valuePerHour.**",
		
		
		
		"/project/**",
		"/projectDetail/**",
		"/updateProjectDetail/**",
		"/additional_hour/**",
		"/updatePointMember/**",
		"/task/**",
		"/taskDetail/**",
		"/updateTaskDetail/**",
		"/reporting/**",
		"/projectReporting/**",
		"/skillSetReporting/**",
		"/taskReporting/**",
		"/setting/**",
		"/newUser/**",
		"/batch/**",
		"/createBatch/**",
		"/showUpdateBatch/**",
		"/kitpoint/**",
		"/view_update_point/**",
		"/viewPoint/**",
		"/updateAllPoint/**",
		"/kitpoint_value/**",
		"/projectCategory/**",
		"/valuePerHour/**",

		
 
    };
	private static final String[] ADMIN_MATCHERS = {

		"/projectAdminView/**",
		"/updateProjectDetailAdminView/**",
		"/taskDetailAdminView/**",
		"/taskAdminView/**",
		"/updateTaskDetailAdminView/**",
		"/ProjectNTaskAdmin/**",
		"/additional_hour_admin/**",
		
		
		
		"/projectAdminView.**",
		"/updateProjectDetailAdminView.**",
		"/taskDetailAdminView.**",
		"/taskAdminView.**",
		"/updateTaskDetailAdminView.**",
		"/ProjectNTaskAdmin.**",
		"/additional_hour_admin.**",
		
   
    };
	private static final String[] USER_MATCHERS = {

		"/projectUserView/**",
		"/projectDetailUserView/**",
		"/taskDetailUserView/**",
		"/taskUserView/**",
		
		
		"/projectUserView.**",
		"/projectDetailUserView.**",
		"/taskDetailUserView.**",
		"/taskUserView.**",
		
   
    };
	private static final String[] PUBLIC_MATCHERS = {
        "/",
		"/profile/**",
		"/updateTask**",
		"/ProjectNTask.**",
		"/ProjectNTask/**",
   
    };
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		http.authorizeRequests()
		.antMatchers(SUPER_ADMIN_MATCHERS).access("hasRole('ROLE_ADMIN')").and().formLogin().loginPage("/login").failureUrl("/login?error")
				.usernameParameter("username")
				.passwordParameter("password")
				.and().logout().logoutSuccessUrl("/login?logout")
				.and().csrf()
				.and().exceptionHandling().accessDeniedPage("/403");
	/*access right for admin */
		http.authorizeRequests()
        .antMatchers(ADMIN_MATCHERS).access("hasRole('ROLE_N_ADMIN')").and().formLogin().loginPage("/login").failureUrl("/login?error")
		   .usernameParameter("username")
		   .passwordParameter("password")
		   .and().logout().logoutSuccessUrl("/login?logout")
		   .and().csrf()
		   .and().exceptionHandling().accessDeniedPage("/403");
		
		http.authorizeRequests()
        .antMatchers(USER_MATCHERS).access("hasRole('ROLE_USER')").and().formLogin().loginPage("/login").failureUrl("/login?error")
		   .usernameParameter("username")
		   .passwordParameter("password")
		   .and().logout().logoutSuccessUrl("/login?logout")
		   .and().csrf()
		   .and().exceptionHandling().accessDeniedPage("/403");
		http.authorizeRequests()
        .antMatchers(PUBLIC_MATCHERS).access("hasRole('ROLE_USER')or hasRole('ROLE_ADMIN') or hasRole('ROLE_N_ADMIN') ").and().formLogin().loginPage("/login").failureUrl("/login?error")
		   .usernameParameter("username")
		   .passwordParameter("password")
		   .and().logout().logoutSuccessUrl("/login?logout")
		   .and().csrf()
		   .and().exceptionHandling().accessDeniedPage("/403");
					
	}
		
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
}




	
	
	
	
	
	
	
	
	
	
	
	
