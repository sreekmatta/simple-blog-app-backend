package com.example.simpleblogappbackend.services;

import java.util.Optional;

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
@CrossOrigin(origins = "*")
public class BlogService {

	@Autowired
	BlogRepository blogRepository;

	@PostMapping("/api/blog")
	public ResponseEntity<HttpStatus> createBlog(@RequestBody Blog blog, HttpSession session) {
		Blog savedBlog = blogRepository.save(blog);
		if (savedBlog != null) {
			return ResponseEntity.ok(HttpStatus.OK);
		}
		return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
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
	public Optional<Blog> findBlogById(@PathVariable("id") int id, HttpSession session) {
		return blogRepository.findById(id);
	}

	@DeleteMapping("/api/blog/{id}")
	public void deleteBlogById(@PathVariable("id") int id, HttpSession session) {
		blogRepository.deleteById(id);
	}

}
