package com.techm.crypton.jericho.controller;

import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techm.crypton.jericho.services.ExcelService;

@CrossOrigin(origins="*" , maxAge = 3600)
@RestController
@RequestMapping("/api/excel")
public class FileController {
	
	private final ExcelService fileService;

	public FileController(ExcelService fileService) {
		this.fileService = fileService;
	}
	
	@PostMapping("/upload")
	public ResponseEntity<List<Integer>> uploadFile(@RequestParam("file")MultipartFile file, @RequestParam("createdBy") String createdBy) {
		try {
			return fileService.saveIntoDatabase(file,createdBy);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/download/{entity}")
	public ResponseEntity<Resource> getFile(@PathVariable("entity") String entity) {
		return fileService.load(entity);
	}
}
