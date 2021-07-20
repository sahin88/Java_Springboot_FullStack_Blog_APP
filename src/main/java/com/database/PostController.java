package com.database;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import com.database.post.entity.Post;
import com.database.postservice.PostService;



@Controller
public class PostController {
	
	@Autowired
	PostService postservice;
	
	@PostMapping("/update/{id}")
	public String UpdatePage(Model model, @PathVariable(name="id") Long id) {
		
		
		Post post = postservice.getPostById(id);
		
		model.addAttribute("brands",post);
		return "update";
		
	}
	

}
