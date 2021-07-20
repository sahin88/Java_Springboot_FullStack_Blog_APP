package com.database.websecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.database.customoauthtwo.CustomOauth2Service;
import com.database.customoauthtwo.Oauth2LoginSuccessHandler;





	 
	@Configuration
	@EnableWebSecurity
	public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	 
	    @Bean
	    public UserDetailsService userDetailsService() {
	        return new UserDetailImp();
	    }
	    @Autowired
	    CustomOauth2Service customautoservice ;
	    

	    @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	     
	    @Bean
	    public DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(userDetailsService());
	        authProvider.setPasswordEncoder(passwordEncoder());
	         
	        return authProvider;
	    }
	    
	 
	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.authenticationProvider(authenticationProvider());
	    }
	  // . antMatchers("/**").permitAll()         .antMatchers("/").hasAnyAuthority("USER", "CREATOR", "EDITOR", "ADMIN") 
//        .antMatchers("/users").authenticated()
//        .anyRequest().permitAll()
//        .and()
//        .formLogin()
//            .usernameParameter("email")
//            .defaultSuccessUrl("/users")
//            .permitAll()
//        .and()
//        .logout().logoutSuccessUrl("/").permitAll();
	    //.failureUrl("/login_error"), logoutsuccessHandler .successHandler(oauthsuccesshandler)
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.authorizeRequests()
            	.antMatchers("/resources/**", "/static/**","/webjars/**","/verify/**","/forgot_password_form/**","/token/**","/register/**","/oauth2/**").permitAll()  
	            .antMatchers("/**","/static/**","/resources/**").permitAll()
	            .antMatchers("/new").hasAnyAuthority("ADMIN", "CREATOR")
	            .antMatchers("/edit/**").hasAnyAuthority("ADMIN", "EDITOR")
	            .antMatchers("/delete/**").hasAuthority("ADMIN")
	            .anyRequest().authenticated()
	            .and()
	            .formLogin().permitAll().loginPage("/login") 
	            .usernameParameter("email")
	            .passwordParameter("pass")
	            .loginProcessingUrl("/doLogin").defaultSuccessUrl("/", true)
	            .failureForwardUrl("/login_failure")
	            .and()
				.oauth2Login()
				.loginPage("/login")
				.userInfoEndpoint().userService(customautoservice)
				.and() 
				.successHandler(oauthsuccesshandler)
				.and()
	            .logout().logoutSuccessUrl("/login").permitAll()
	            .and()
	            .exceptionHandling().accessDeniedPage("/403");

}
	    
		@Autowired
		Oauth2LoginSuccessHandler oauthsuccesshandler;
	     
	    
	}
	
	
