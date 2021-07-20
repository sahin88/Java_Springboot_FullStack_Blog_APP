package com.database.websecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.database.user.entity.User;
import com.database.user.repository.UserRepository;
import com.database.userdetail.MyUserDetail;

public class UserDetailImp implements UserDetailsService {
	
	@Autowired
	UserRepository userrepo;

	 @Override
	    public UserDetails loadUserByUsername(String email)
	            throws UsernameNotFoundException {
	        User user = userrepo.getUserByEmail(email);
	         
	        if (user == null) {
	            throw new UsernameNotFoundException("Could not find user");
	        }
	         
	        return new MyUserDetail(user);
	    }

}
