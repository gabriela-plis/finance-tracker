package com.financetracker.app.report.generators;

import com.financetracker.app.report.types.Report;

import java.util.List;

public interface ReportsGenerator {
    List<Report> generate();
}
