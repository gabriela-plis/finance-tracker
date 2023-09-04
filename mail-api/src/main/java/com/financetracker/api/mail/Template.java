package com.financetracker.api.mail;

import lombok.Getter;

@Getter
public enum Template {
    GENERAL_WEEKLY_REPORT("Your personal general weekly report"),
    GENERAL_MONTHLY_REPORT("Your personal general monthly report"),
    GREETING("Welcome!");

    final String title;

    Template(String title) {
        this.title = title;
    }
}