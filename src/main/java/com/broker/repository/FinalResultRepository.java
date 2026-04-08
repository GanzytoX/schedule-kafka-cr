package com.broker.repository;
import com.broker.model.ProcessedResult;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FinalResultRepository extends MongoRepository<ProcessedResult, String> {}
