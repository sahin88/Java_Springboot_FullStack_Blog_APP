package com.database;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.database.user.entity.User;
import com.database.user.repository.UserRepository;
import com.database.usernotexception.UserNotFoundException;

import net.bytebuddy.utility.RandomString;

@Controller
public class ForgetPasswordController {
	@Autowired
	UserRepository userrepo;
	
	@Autowired
	JavaMailSender mailSender;
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }  
	@RequestMapping("/forgot_password_form")
	public String forgotPasswordPassword(Model model) {
		String message=" Please enter your email to reset\n"+ "your password !";
		model.addAttribute("message", message);
		return "password_forgot_form";		
	}
    @PostMapping("/forgot_password_form/submit")
	public  String updateResetPasswordToken(HttpServletRequest request, Model model) throws UnsupportedEncodingException, MessagingException, UserNotFoundException {
		String email= request.getParameter("email");
		String resetPasswordToken= RandomString.make(45);
		
		User user = userrepo.getUserByEmail(email);
		if (user !=null ) {
			user.setResetPasswordToken(resetPasswordToken);
			userrepo.save(user);
			sendResetVerificationCode( user, getSiteURL(request));
			String message="We have sent to you an email\n Please Check your e-mail address";
			model.addAttribute("message", message);
			return "password_forgot_form";
		}
		
		else {	
			String error="User could not be found\n Please Check your e-mail address";
			model.addAttribute("message", error);
			return "password_forgot_form";
		}
	}
    
    public void sendResetVerificationCode(User user, String SiteURL) throws UnsupportedEncodingException, MessagingException {
		String subject="please verify your Registration";
		String senderName="Sahin Blog";
		String mailContent="<p> Dear "+user.getFirstname()+" "+ user.getLastname()+" ,"+"</p>";
		mailContent+="Please click the link below to verify your Account!";
		String verifyURL=SiteURL+"/token?code="+user.getResetPasswordToken();
		System.out.println("acanimda doldur icelim  :: "+verifyURL);
		mailContent+="<h3><a href=\""+verifyURL+"\">VERIFY</a></h3>";
		System.out.println("verifyURL   "+verifyURL);
		mailContent+="Fuck you alll!";
		MimeMessage message=mailSender.createMimeMessage();
		MimeMessageHelper helper= new MimeMessageHelper(message);
		helper.setFrom("sahinogur88@gmail.com",senderName);
		helper.setTo(user.getEmail());
		helper.setSubject(subject);
		helper.setText(mailContent, true);
		mailSender.send(message);		
	}
    
    @GetMapping("/token")
	public String token(@Param("code") String code, Model model) {
    	
		User user= userrepo.getUserByResetPasswordToken(code);
		if(user ==null) {
			String error="Dear User, \n Your verificarion code is not valid, \n Please get new verification Code";
			model.addAttribute("error", error);
			return "message";
		}
		else {
			user.setResetPasswordToken(null);
			System.out.println("user email :: "+user.getEmail());
			model.addAttribute("email", user.getEmail());
			return "password_forgot_change";
		
		}
			
	}
	
	   @PostMapping("/forgot_password_form/change")
		public  String changeUserPasword(HttpServletRequest request, Model model) throws UnsupportedEncodingException, MessagingException, UserNotFoundException {
			String email= request.getParameter("email");
			String password= request.getParameter("password");
			User user = userrepo.getUserByEmail(email);
			user.setPassword(password);
			user.setResetPasswordToken(null);
			userrepo.save(user);
			String success= "You have sucessfully changed the password!";
			model.addAttribute("success", success);
			return "message";
		}

}
