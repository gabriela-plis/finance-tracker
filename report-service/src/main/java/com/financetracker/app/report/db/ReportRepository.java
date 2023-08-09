package com.financetracker.app.report.db;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportRepository extends MongoRepository<ReportEntity, String> {
}
