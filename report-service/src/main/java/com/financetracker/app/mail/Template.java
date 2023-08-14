package com.financetracker.app.mail;

import lombok.Getter;

@Getter
public enum Template {
    GENERAL_WEEKLY_REPORT("your personal general weekly report"),
    GENERAL_MONTHLY_REPORT("your personal general monthly report"),
    GREETING("welcome!");

    final String title;

    Template(String title) {
        this.title = title;
    }
}
