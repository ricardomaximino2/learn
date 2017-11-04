package com.example.demo.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.UserRepository;
import com.example.demo.beans.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Value")
@RequestMapping("/rest")
@RestController
public class MyRest {

	@Autowired
	private UserRepository userRepository;

	@GetMapping(path="/user")
	@ApiOperation(value="Provide all users from the server without pagination")
	@ApiResponses(value={ @ApiResponse(code=200,message="Everything is alright"),@ApiResponse(code=100,message="Something look unfamilire")})
	public List<User> getAll() {
		List<User> list = new ArrayList<>();
		for (User user : userRepository.findAll()) {
			list.add(user);
		}
		return list;
	}

	@GetMapping(path = "/user/pdf", produces = "application/pdf")
	public List<User> getPdf(HttpServletResponse response) {
		// response.setContentType("application/pdf");

		List<User> list = new ArrayList<>();
		for (User user : userRepository.findAll()) {
			list.add(user);
		}
		return list;
	}

	@RequestMapping(path = "/user/download", method = RequestMethod.GET)
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 File file = new File("C:\\Users\\Ricardo\\Pictures\\sin nome\\Immagine 046.jpg");
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		String mineType = URLConnection.guessContentTypeFromStream(inputStream);

		if (mineType == null) {
			mineType = "application/octet-stream";
		}

		response.setContentType(mineType);
		response.setContentLength((int) file.length());
		response.setHeader("Content-Diposition", String.format("attachment; filename=\"%s\"", file.getName()));

		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}

	@ExceptionHandler(IOException.class)
	public String handleErros(Exception ex) {
		return ex.getMessage();
	}

	@GetMapping(value = "/user/{id}")
	public User findUser(@RequestParam(value = "id", required = true) long id) {
		return userRepository.findOne(id);
	}

}
