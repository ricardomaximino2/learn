package com.example.demo.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DowloadController {
	
	
	@RequestMapping("/txt/download")
	public void downloadWithServlet(HttpServletResponse response) throws IOException {
		final String filename = " testdownload.txt";
		List<String> contents = Arrays.asList(
				"My name is Ricardo Maximino Goncalves de Moraes",
				"My mother is Irene Goncalves",
				"My sister is Claudia Fabiana Bufe", 
				"My brother is Paulo Roberto Sandström", 
				"My wife is Africa Chacopino Chacopino",
				"My daugther is Suhaila Chacopino Goncalves");
		
		response.setContentType("text/xml");
		response.addHeader("Content-Disposition", "attachment; filename=" + filename);
		response.setContentLength(listLength(contents));
		response.setStatus(200);
		ServletOutputStream out = response.getOutputStream();
		PrintWriter print = new PrintWriter(out);
		for(String s : contents) {
			print.println(s);//for me, the more elegant way
//			print.format("%s%n", s);//using the regular string formatter
		}
		print.close();
		out.close();
		
	}
	
	
	
	private int listLength(List<String> list) {
		int length = 0;
		for(String s : list) {
			length += s.length();
		}
		return length + ((list.size()*2));
	}

}
