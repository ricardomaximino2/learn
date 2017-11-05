package com.example.demo.controllers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
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
				"My name is Ricardo Maximino Gonçalves de Moraes",
				"My mother is Irene Gonçalves",
				"My sister is Claudia Fabiana Bufe", 
				"My brother is Paulo Roberto Sandström", 
				"My wife is Africa Chacopino Chacopino",
				"My daugther is Suhaila Chacopino Gonçalves",
				"My mother is Irene Gonçalves",
				"My sister is Claudia Fabiana Bufe", 
				"My brother is Paulo Roberto Sandström", 
				"My wife is Africa Chacopino Chacopino",
				"My daugther is Suhaila Chacopino Gonçalves");
		
		response.setContentType("text/xml");
		response.addHeader("Content-Disposition", "attachment; filename=" + filename);
		//response.setContentLength(listLength(contents));
		response.setStatus(200);
		
		Path path = Paths.get("tr.txt");
		if(!Files.exists(path, LinkOption.NOFOLLOW_LINKS))Files.createFile(path);
		BufferedWriter bw = Files.newBufferedWriter(path,StandardCharsets.UTF_8);
		PrintWriter fileOut = new PrintWriter(bw);
		for(String s : contents) {
			fileOut.println(s);
			fileOut.flush();
			System.out.println(Files.size(path));
		}
		response.setContentLengthLong(Files.size(path));
		ServletOutputStream out = response.getOutputStream();
		Files.copy(path, out);
		out.close();
		bw.close();
		fileOut.close();
		System.out.println("File length is: " + Files.size(path));
		System.out.println("Download length is: " + listLength(contents));
		Files.delete(path);
	}
	
	
	
	private int listLength(List<String> list) {
		int length = 0;
		for(String s : list) {
			length += s.toCharArray().length;
			for(Character c : s.toCharArray()) {
				if(c.hashCode()>160) length++;
			}
		}
		return length + ((list.size()*2));
	}

}
