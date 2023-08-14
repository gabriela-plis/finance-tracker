package com.financetracker.app.mail;

import lombok.Builder;

import java.util.Map;

@Builder
public record MailDTO(
    Template template,
    String recipient,
    String title,
    Map<String, Object> templateProperties
) { }
