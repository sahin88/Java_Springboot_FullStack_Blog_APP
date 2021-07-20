package com.database.post.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import com.database.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Post {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(length=145, name="title", unique=true)
	private String title;

	@Column(columnDefinition="TEXT", name="content")
	private String content;
	
	@Column(name="created_on")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private String created_on;
	
	
	@Column(name="post_image")
	private String post_image;
	
	
	public String getPost_image() {
		return post_image;
	}

	public void setPost_image(String post_image) {
		this.post_image = post_image;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	
	
	private User user;

	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", content=" + content + ", created_on=" + created_on + ", user="
				+ user + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreated_on() {
		return created_on;
	}

	public void setCreated_on() {
		this.created_on = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
	@Transient
	public String getPhotoPath() {
		if(post_image==null | id == null) return null;
		 
		return "post-dir/posts/"+id +"/"+post_image;
	}
	
	

}
