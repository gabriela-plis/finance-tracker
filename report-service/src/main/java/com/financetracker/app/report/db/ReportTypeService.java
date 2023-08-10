package com.financetracker.app.report.db;

import com.financetracker.app.utils.DocumentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportTypeService {

    private final ReportTypeRepository reportRepository;

    public ReportTypeEntity getReport(ReportType type) {
        return reportRepository.findByName(type)
            .orElseThrow(DocumentNotFoundException::new);
    }
}
