package com.roshan.rest.webservices.restfulwebservices.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roshan.rest.webservices.restfulwebservices.user.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{

}
