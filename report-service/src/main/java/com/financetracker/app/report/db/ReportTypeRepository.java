package com.financetracker.app.report.db;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ReportTypeRepository extends MongoRepository<ReportTypeEntity, String> {

    Optional<ReportTypeEntity> findByName(ReportType reportType);

}
