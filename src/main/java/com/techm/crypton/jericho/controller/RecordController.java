package com.techm.crypton.jericho.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techm.crypton.jericho.models.Records;
import com.techm.crypton.jericho.services.RecordService;

@CrossOrigin 
@RestController
@RequestMapping("/api/records")
public class RecordController {
	private final RecordService recordService;

	@Autowired
	public RecordController(RecordService recordService) {
		this.recordService = recordService;
	}
	
	@PostMapping
	public ResponseEntity<Records> addEntity(@RequestBody Records record) {
		return recordService.addEntity(record);
	}
	
	@GetMapping	
	public ResponseEntity<List<Records>> getAllRecords() {
		return recordService.getAllRecords();
	}
	
	@GetMapping(path = "{entity}")
	public ResponseEntity<List<Records>> getAllRecordsByEntity(@PathVariable("entity") String entity) {
		return recordService.getAllRecordsByEntity(entity);
	}
	
}
