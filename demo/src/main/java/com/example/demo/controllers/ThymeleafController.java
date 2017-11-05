package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.beans.User;
import com.example.demo.repository.UserRepository;

@Controller
public class ThymeleafController {
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/")
	public String hello(Model model) {
		model.addAttribute("hello", "Hello");
		return "index";
	}

	
	@RequestMapping(path="/",method= RequestMethod.POST)
	public String helloWithName(Model model,@RequestParam(required=true,defaultValue="Programmer") String nickname,@ModelAttribute User user,BindingResult userBinding) {
		userRepository.save(user);
		model.addAttribute("nickname", nickname);
		model.addAttribute("user",user);
		model.addAttribute("users",userRepository.findAll());
		return "index";
	}
}
