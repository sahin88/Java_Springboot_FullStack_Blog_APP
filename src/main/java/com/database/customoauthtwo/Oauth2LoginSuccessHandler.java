package com.database.customoauthtwo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.database.authprovider.AuthenticationProvider;
import com.database.user.entity.User;
import com.database.user.repository.UserRepository;



@Component
public class Oauth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@Autowired
	UserRepository repo;
	
	@SuppressWarnings("unused")
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
       
		CustomOAuth2User oauth2user=  (CustomOAuth2User) authentication.getPrincipal();
//		System.out.println("authentication ::"+"oauth2user::: "+oauth2user.getAttribute("name"));
//		String username=oauth2user.getAttribute("name");
		String username=oauth2user.getName();
		User user=repo.getUserByEmail(username);
		System.out.println("user from  succeshandler"+ user);
		if (user==null ) {
			User usr= new User();
			usr.setEnabled(true);
			usr.setEmail(oauth2user.getAttribute("email"));
			usr.setFirstname(oauth2user.getName());
			usr.setLastname("null");
			usr.setPassword("null");
			usr.setAuthprovider(AuthenticationProvider.GOOGLE);
			repo.save(usr);
			System.out.println("New User");
		}
		else {
			System.out.println("Existing Customer");
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}


}
