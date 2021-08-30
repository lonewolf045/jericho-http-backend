package com.techm.crypton.jericho.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.techm.crypton.jericho.models.Records;
import com.techm.crypton.jericho.repository.RecordRepository;

@Service
public class RecordService {
	private final RecordRepository recordRepo;

	@Autowired
	public RecordService(RecordRepository recordRepo) {
		this.recordRepo = recordRepo;
	}
	
	public ResponseEntity<Records> addEntity(Records record) {
		try {
			Records newRec = recordRepo.save(new Records(record.getYear(),record.getPeriod(),record.getEntity(),record.getNumberOfItems()));
			return new ResponseEntity<>(newRec,HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<List<Records>> getAllRecords() {
		try {
			List<Records> records = new ArrayList<Records>();
			recordRepo.findAll().forEach(records::add);
			if(records.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(records,HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<List<Records>> getAllRecordsByEntity(String entity) {
		try {
			List<Records> records = new ArrayList<Records>();
			records = recordRepo.findByEntityContaining(entity);
			if(records.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(records,HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
