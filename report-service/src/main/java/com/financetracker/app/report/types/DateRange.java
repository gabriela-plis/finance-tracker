package com.financetracker.app.report.types;

import java.time.LocalDate;

public record DateRange(
    LocalDate startDate,
    LocalDate endDate
) { }
