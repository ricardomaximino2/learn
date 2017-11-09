package com.example.demo.controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.example.demo.services.StorageService;
 
@Controller
public class UploadController {
 
	@Autowired
	StorageService storageService;
 
	List<String> files = new ArrayList<String>();
 
 
	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
		try {
			storageService.store(file);
			model.addAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
			files.add(file.getOriginalFilename());
			model.addAttribute("files",
					files.stream()
							.map(fileName -> MvcUriComponentsBuilder
									.fromMethodName(UploadController.class, "getFile", fileName).build().toString())
							.collect(Collectors.toList()));
			model.addAttribute("totalFiles", "TotalFiles: " + files.size());
		} catch (Exception e) {
			System.out.println(e);
			model.addAttribute("message", "FAIL to upload " + file.getOriginalFilename() + "!");
		}
		return "index";
	}
 
	@GetMapping("/upload")
	public String getListFiles(Model model) {
		model.addAttribute("files",
				files.stream()
						.map(fileName -> MvcUriComponentsBuilder
								.fromMethodName(UploadController.class, "getFile", fileName).build().toString())
						.collect(Collectors.toList()));
		model.addAttribute("totalFiles", "TotalFiles: " + files.size());
		return "index";
	}
 
	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = storageService.loadFile(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
}