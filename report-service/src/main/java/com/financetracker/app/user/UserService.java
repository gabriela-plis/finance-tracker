package com.financetracker.app.user;

import com.financetracker.app.report.types.ReportType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public List<UserEntity> getReportSubscribers(ReportType reportType) {
        return null;
    }
}
