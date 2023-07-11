package com.roshan.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.roshan.rest.webservices.restfulwebservices.jpa.PostRepository;
import com.roshan.rest.webservices.restfulwebservices.jpa.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {

	private UserRepository service;
	private PostRepository postService;

	public UserJpaResource(UserRepository service, PostRepository postService) {
		this.service = service;
		this.postService = postService;
	}

	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return service.findAll();
	}

	
	 @GetMapping("/users/{id}") 
	 public EntityModel<User> retrieveUser(@PathVariable int id){ 
		 Optional<User> user = service.findById(id);
	  
		 if(user.isEmpty()) 
			 throw new UserNotFoundException("id:"+id);
	  
		 EntityModel<User> entityModel = EntityModel.of(user.get()); 
		 WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		 entityModel.add(link.withRel("all-users"));
		 return entityModel; 
	 }

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		service.deleteById(id);
	}

	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/users/{id}/posts")
	public List<Post> retrievePostsForAUser(@PathVariable int id) {
		Optional<User> user = service.findById(id);
		  if(user.isEmpty()) 
			  throw new UserNotFoundException("id:"+id);
		List<Post> posts = user.get().getPosts();
		return posts;
	}
	
	@PostMapping("/users/{id}/posts")
	public ResponseEntity<Post> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post) {
		Optional<User> user = service.findById(id);
		  if(user.isEmpty()) 
			  throw new UserNotFoundException("id:"+id);
		  post.setUser(user.get());
		Post savedPost = postService.save(post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedPost.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/users/{id}/posts/{p_id}")
	public Post retrievePostForAUser(@PathVariable int id, @PathVariable int p_id) {
		Optional<User> user = service.findById(id);
		  if(user.isEmpty()) 
			  throw new UserNotFoundException("id:"+id);
		Post post = user.get().getPosts().get(p_id);
		return post;
	}
}
