package com.financetracker.app.report.generators;

import com.financetracker.api.mail.Template;
import com.financetracker.app.report.types.Report;

import java.util.List;

public interface ReportsGenerator<T extends Report> {
    List<T> generate();
    Template getTemplate();
}
