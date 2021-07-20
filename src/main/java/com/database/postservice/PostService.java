package com.database.postservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.database.post.entity.Post;
import com.database.post.repository.PostRepository;


	
	@Service
	public class PostService {
		
		
		@Autowired
		PostRepository postrepo;
		
		public List<Post> getAllPost(){
			
			return postrepo.findAll();
		}
		public Post SavePost(Post post) {
			return postrepo.save(post);
			
		}
		
		public Post getPostById(Long Id) {
			return postrepo.findById(Id).get();
		}
		
		public void DeletePostById(Long id) {
			System.out.println("Pir sultan abdalim "+id);
			postrepo.deleteById(id);
		}
	
}

