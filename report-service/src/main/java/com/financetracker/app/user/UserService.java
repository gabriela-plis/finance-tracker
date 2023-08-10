package com.financetracker.app.user;

import com.financetracker.app.report.db.ReportTypeService;
import com.financetracker.app.report.db.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ReportTypeService reportService;

    public List<UserEntity> getReportSubscribers(ReportType reportType) {
        return reportService.getReport(reportType).getSubscribers();
    }
}
