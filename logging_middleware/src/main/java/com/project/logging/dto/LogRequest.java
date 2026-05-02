package com.project.logging.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LogRequest {

    private String stack;
    private String level;

    @JsonProperty("package")
    private String packageName;

    private String message;

    public LogRequest(String stack, String level, String packageName, String message) {
        this.stack = stack;
        this.level = level;
        this.packageName = packageName;
        this.message = message;
    }

    public String getStack() { return stack; }
    public String getLevel() { return level; }
    public String getPackageName() { return packageName; }
    public String getMessage() { return message; }
}