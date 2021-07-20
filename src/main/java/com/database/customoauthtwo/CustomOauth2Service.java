package com.database.customoauthtwo;


import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
public class CustomOauth2Service  extends DefaultOAuth2UserService{
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("userRequest            :: "+userRequest);
		 OAuth2User user= super.loadUser(userRequest);
		 System.out.println("user  from CustomOauth2Servie  page");
		return  new CustomOAuth2User(user);
	}

}
