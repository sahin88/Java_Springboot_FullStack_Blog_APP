package com.database.post.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.database.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	
//	@Transactional

//	@Query("DELETE  from  Post WHERE  Post.id = ?1")
//	void Postdelete(Long id);

}
