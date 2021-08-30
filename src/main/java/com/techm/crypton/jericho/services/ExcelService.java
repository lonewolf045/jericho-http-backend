package com.techm.crypton.jericho.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.techm.crypton.jericho.helpers.ExcelHelper;
import com.techm.crypton.jericho.models.Records;
import com.techm.crypton.jericho.repository.RecordRepository;

@Service
public class ExcelService {
	private final RecordRepository recordRepo;

	@Autowired
	public ExcelService(RecordRepository recordRepo) {
		this.recordRepo = recordRepo;
	}
	
	public ResponseEntity<List<Integer>> saveIntoDatabase(MultipartFile file,String createdBy) {
		try {
			List<Records> records = ExcelHelper.excelToRecords(file.getInputStream());
			String entity = records.get(0).getEntity();
			String period = records.get(0).getPeriod();
			int add = 0, wrong = 0;
			for( Records record : records) {
				if(record.getEntity() == entity && record.getPeriod() == period) {
						recordRepo.save(new Records(record.getYear(),record.getPeriod(),record.getEntity(),record.getNumberOfItems(),createdBy));
						add+=1;
				} else {
					wrong+=1;
				}
			}
			List<Integer> res = new ArrayList<Integer> ();
			res.add(add);
			res.add(wrong);
			return new ResponseEntity<>(res,HttpStatus.CREATED);
		} catch (IOException e) {
			//throw new RuntimeException("fail to store excel data: " + e.getMessage());
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	public ResponseEntity<Resource> load(String entity) {
		try {
			List<Records> records = recordRepo.findByEntityContaining(entity);
			if(records.size() == 0)
				throw new Exception("No record present");
			ByteArrayInputStream fileNew = ExcelHelper.recordsToExcel(records);
			String filename = "download.xlsx";
			InputStreamResource file = new InputStreamResource(fileNew);
			return ResponseEntity.ok()
			        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
			        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
			        .body(file);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
	}
	
}
