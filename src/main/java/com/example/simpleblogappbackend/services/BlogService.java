package com.example.simpleblogappbackend.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.simpleblogappbackend.models.Blog;
import com.example.simpleblogappbackend.repository.BlogRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class BlogService {

	@Autowired
	BlogRepository blogRepository;

	@PostMapping("/api/blog")
	public Blog createBlog(@RequestBody Blog blog, HttpSession session) {
		return blogRepository.save(blog);
		
	}

	@PutMapping("/api/blog")
	public ResponseEntity<HttpStatus> updateBlog(@RequestBody Blog newblog, HttpSession session) {

		Optional<Blog> updatedBlogOptional = blogRepository.findById(newblog.getId());

		if (updatedBlogOptional.isPresent()) {
			Blog updatedBlog = updatedBlogOptional.get();
			if (updatedBlog != null) {
				updatedBlog.set(newblog);
				blogRepository.save(updatedBlog);
				return ResponseEntity.ok(HttpStatus.OK);
			}
		}
		return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/api/blog/{id}")
	public Blog findBlogById(@PathVariable("id") int id, HttpSession session, 
			HttpServletResponse response) {
		Optional<Blog> data = blogRepository.findById(id);
		if(data.isPresent()) {
			return data.get();
		}
		else {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return null;
		}
	}
	
	@GetMapping("/api/blog")
	public List<Blog> finaAllBlogs( HttpSession session,HttpServletResponse response) {
		return blogRepository.findAll();
		
	}

	@DeleteMapping("/api/blog/{id}")
	public void deleteBlogById(@PathVariable("id") int id, HttpSession session) {
		blogRepository.deleteById(id);
	}

}
