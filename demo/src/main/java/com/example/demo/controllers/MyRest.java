package com.example.demo.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.beans.User;
import com.example.demo.services.PdfService;
import com.example.demo.services.UserService;
import com.itextpdf.text.DocumentException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Value")
@RequestMapping("/rest")
@RestController
public class MyRest {
	private final Logger log = LoggerFactory.getLogger(MyRest.class);

	private static final String PDF = "dowload.pdf";
	@Autowired
	private UserService userService;
	@Autowired
	private PdfService pdfService;
	
	@PostMapping(value="/user")
	public User createUser(@ModelAttribute User user, BindingResult result) {
		if(result.hasErrors()) {
			log.info(result.getObjectName());
		}
		return userService.save(user);
	}
	
	@GetMapping(value = "/user/{id}")
	public User getUserById(@PathVariable(value = "id", required = true) long id) {
		return userService.getUserById(id);
	}
	
	@DeleteMapping(value = "/user/{id}")
	public boolean deleteUserById(@PathVariable(value = "id", required = true) long id) {
		return userService.deleteUserById(id);
	}

	@GetMapping(path="/user")
	@ApiOperation(value="Provide all users from the server without pagination")
	@ApiResponses(value={ @ApiResponse(code=200,message="Everything is alright"),
	@ApiResponse(code=404,message="Something look unfamilire"),
	@ApiResponse(code=202,message="If the list is empty")})
	public List<User> getAll() {
		return userService.getAllUsers();
	}

	@GetMapping(path = "/user/downloa/pdf", produces = "application/pdf")
	public void downloadPdf(HttpServletResponse response) throws IOException, DocumentException {
		Path path = Paths.get(PDF);
		pdfService.createPdf();
		ServletOutputStream out = setResponsePdf(response);
		Files.copy(path, out);
	}
	
	@GetMapping(value="/user/download/txt", produces="txt/xml")
	public void downloadTxt(HttpServletResponse response) throws IOException {
		List<User> list = userService.getAllUsers();
		PrintWriter print = new PrintWriter(setResponse(response, list));
		for(User user : list) {
			print.println(user);
		}
		print.close();
	}

	@RequestMapping(path = "/user/download/{file}.{ext}", method = RequestMethod.GET)
	public void download(@PathVariable String file, @PathVariable String ext,HttpServletRequest request, HttpServletResponse response) throws IOException {
		Path path = Paths.get("C:/Users/Ricardo/Pictures/sin nome/" + file + "." + ext);
		String contentType = URLConnection.guessContentTypeFromName(path.getFileName().toString());
		if (contentType == null) contentType = "application/octet-stream";
		response.setContentType(contentType);
		response.setContentLengthLong(Files.size(path));
		response.setHeader("Content-Diposition", "attachment; filename=" + path.getFileName());
		Files.copy(path, response.getOutputStream());
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<String> handleIOErros(Exception ex) {
		return new ResponseEntity<String>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> handleNoSuchElementErros(Exception ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	private ServletOutputStream setResponsePdf(HttpServletResponse response) throws IOException {
		Path path = Paths.get(PDF);
		response.setContentLength((int)Files.size(path));
		System.out.println(Files.size(path));
		response.setContentType("application/pdf");
		response.setCharacterEncoding("ISO-8859-1");
		response.setStatus(200);
		response.addHeader("Content-Disposition", "attachment; filename=download.pdf");
		return response.getOutputStream();
	}
	
	private ServletOutputStream setResponse(HttpServletResponse response,List<User> list) throws IOException {
		response.setContentLength(countLength(list));
		response.setContentType("txt/xml");
		response.setCharacterEncoding("ISO-8859-1");
		response.setStatus(200);
		response.addHeader("Content-Disposition", "attachment; filename=download.txt");
		return response.getOutputStream();
	}
	
	private int countLength(List<User> list) {
		int length = 0;
		for(User user : list) {
			length += user.toString().getBytes().length;
		}
		return length + (list.size()*2);
	}

}
