package com.financetracker.app.report.types;

import java.time.LocalDate;

public record DateInterval(
    LocalDate startDate,
    LocalDate endDate
) { }
