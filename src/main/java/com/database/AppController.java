package com.database;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.database.post.entity.Post;
import com.database.post.repository.PostRepository;
import com.database.postservice.PostService;
import com.database.role.entites.Role;
import com.database.user.entity.User;
import com.database.user.repository.UserRepository;
import com.database.user.service.UserService;
import net.bytebuddy.utility.RandomString;
@Controller
public class AppController {
	@Autowired
	UserService userservice;
	
	@Autowired
	PostRepository postrepo;
	
	@Autowired
	private UserRepository userrepo;
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	PostService postservice;
	
	
	
	@RequestMapping("/")

	public String Homepage(Model model) {
		
		List<Post> postlist= postservice.getAllPost();
		model.addAttribute("postslists",postlist);
		
		return "posts";
		}
	
	@RequestMapping("/postform")
	public String PostFormPage(Model model) {
		System.out.println("post saving");
		Post post = new Post();
		model.addAttribute("posts",post);

		return "postform";
		}
	
	
	@RequestMapping("/read/{id}")
	public String ReadPage(Model model, @PathVariable(name="id") Long id) {
		
		Post post = postservice.getPostById(id);
		System.out.println("post  :  "+post.getCreated_on());
		String imagepath= "http://localhost:8080/"+post.getPhotoPath();
		model.addAttribute("posts",post);
		model.addAttribute("imagepath",imagepath);
		return "readmore";
		
	}
	
	
	@PostMapping("/delete/{id}")
	public String PostDelete(@PathVariable(name="id") Long id,RedirectAttributes ra) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userrepo.getUserByEmail(auth.getName());
		
		Post post = postservice.getPostById(id);
		if(user==null) {
			ra.addFlashAttribute("error", "User is not logged in");
			return "redirect:/login";
		}
		
		if(user.getId()==post.getUser().getId()) {
			//x
			String deleteFile= "src/main/resources/static/post-dir/" +"posts/"+id+"/"+post.getPost_image();
			File file= new File(deleteFile);
			File dir= new File(file.getParent());
			System.out.println("name  dir  :"+dir+"name file   ::"+file);
			if(file.delete()) {
				FileUtils.deleteDirectory(dir);
				postservice.DeletePostById(id);
				ra.addFlashAttribute("sucess", id+" Photo has been deleted");
				return "redirect:/";
				
			}
			
			
		}
		else {
			ra.addFlashAttribute("error", "You are not allowed to delete this post!");
			return "redirect:/";
			
		}
		return null;

	}
	

	@RequestMapping("/postform/save")

	public String PostFormSave(@ModelAttribute(name="brands") Post post, RedirectAttributes ra,Model model, @RequestParam("fileImage") MultipartFile multipart) throws IOException {
		String fileName=StringUtils.cleanPath(multipart.getOriginalFilename());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		post.setPost_image(fileName);  
		post.setCreated_on();
		post.setUser(userrepo.getUserByEmail(auth.getName()));
		
		
		Post savedPost=postservice.SavePost(post);
		
		String uploaddir= "src/main/resources/static/post-dir/" +"posts/"+ savedPost.getId();
		Path uploadpath= Paths.get(uploaddir);
		
		if(!Files.exists(uploadpath)){
			Files.createDirectories(uploadpath);
		}
		try(InputStream inputstream =multipart.getInputStream()){
		Path filePath=uploadpath.resolve(fileName);
		Files.copy(inputstream,filePath, StandardCopyOption.REPLACE_EXISTING);}
		catch (IOException e) {
			throw new IOException("Could not save the file!"+fileName);
		}
		ra.addFlashAttribute("message",true);
	
		return "redirect:/";
		}


	@RequestMapping("/register")
	public String Registerpage(Model model) {
		User user=new User();
		model.addAttribute("user", user);
		return "registerform";
	}
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }   
	@RequestMapping("/register/save")
	public String FormSave(@ModelAttribute(name="user") User user, RedirectAttributes ra,Model model, @RequestParam("fileImage") MultipartFile multipart, HttpServletRequest request) throws IOException, MessagingException {
		String fileName=StringUtils.cleanPath(multipart.getOriginalFilename());
		String randomStr= RandomString.make(64);
		user.setVerificationCode(randomStr);
		user.getRoles().add(new Role("USER"));
		user.setUser_image(fileName);
		sendVerificationCode(user ,getSiteURL(request));
		User savedUser=userservice.saveUser(user);
		System.out.println("user"+user.toString());
		String uploaddir= "src/main/resources/static/post-dir/" + savedUser.getId();
		Path uploadpath= Paths.get(uploaddir);
		
		if(!Files.exists(uploadpath)){
			Files.createDirectories(uploadpath);
		}
		try(InputStream inputstream =multipart.getInputStream()){
		Path filePath=uploadpath.resolve(fileName);
		Files.copy(inputstream,filePath, StandardCopyOption.REPLACE_EXISTING);}
		catch (IOException e) {
			throw new IOException("Could not save the file!"+fileName);
		}
		ra.addFlashAttribute("message",true);
	
		return "register_success";
	}
	
	public void sendVerificationCode(User user, String SiteURL) throws UnsupportedEncodingException, MessagingException {
		String subject="please verify your Registration";
		String senderName="Sahin Gmbh";
		String mailContent="<p> Dear "+user.getFirstname()+" "+ user.getLastname()+" ,"+"</p>";
		mailContent+="Please click the link below to verify your Account!";
		String verifyURL=SiteURL+"/verify?code="+user.getVerificationCode();
		mailContent+="<h3><a href=\""+verifyURL+"\">VERIFY</a></h3>";
		System.out.println("verifyURL   "+verifyURL);
		mailContent+="KInd regards!";
		MimeMessage message=mailSender.createMimeMessage();
		MimeMessageHelper helper= new MimeMessageHelper(message);
		helper.setFrom("sahinogur88@gmail.com",senderName);
		helper.setTo(user.getEmail());
		helper.setSubject(subject);
		helper.setText(mailContent, true);
		mailSender.send(message);
	
		
	}
	
	public boolean verify(String verification) {
		User user= userrepo.getUserByVerficationCode(verification);
		if(user.isEnabled() || user ==null) {
			return false;
		}
		else {
			System.out.println("user evi yaorul"+user.getId());
			user.setEnabled(true);
			userrepo.save(user);
			return true;
		}
			
	}
	
	 public User getUserByResetPasswordToken(String token) {
	        return userrepo.getUserByResetPasswordToken(token);
	    }
	 public void updatePassword(User user, String newPassword) {
	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        String encodedPassword = passwordEncoder.encode(newPassword);
	        user.setPassword(encodedPassword);
	         
	        user.setResetPasswordToken(null);
	        userrepo.save(user);
	    }
		

	
	@GetMapping("/verify")
	public String VerificationAccount(@Param("code") String code, Model model) {
		boolean verified=verify(code);
		String pageTitle= verified?"Verification has been done sucessfully":"Verification failed!";
		model.addAttribute("pageTitle",pageTitle);
		
		return verified ? "verified_success":"verified_failed";
	}
	@RequestMapping("/login")
	public String LoginPage(Model model) {
		User user=new User();
		model.addAttribute("user", user);
		return "loginform";
	}
	@PostMapping("/login_failure")
	public String LoginFailure() {
		return "login_error";
	}

}
