package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.UserRepository;
import com.example.demo.beans.User;

@RestController
public class MyRest {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(path="/user")
	public List<User> getAll(){
		List<User> list = new ArrayList<>();
		for(User user :userRepository.findAll()) {
			list.add(user);
		}
		return list;
	}
	
	@GetMapping(value="/user/{id}")
	public User findUser(@PathParam("id") long id) {
		return userRepository.findOne(1L);
	}

}
