package com.techm.crypton.jericho.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.techm.crypton.jericho.models.Records;

public interface RecordRepository extends MongoRepository<Records, String> {
	List<Records> findByEntityContaining(String entity);
}
