package com.project.scheduler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LogData {
    private String stack;
    private String level;
    
    @JsonProperty("package")
    private String targetPackage;
    
    private String message;

    public LogData() {}

    public LogData(String stack, String level, String targetPackage, String message) {
        this.stack = stack;
        this.level = level;
        this.targetPackage = targetPackage;
        this.message = message;
    }

    public String getStack() { return stack; }
    public String getLevel() { return level; }
    public String getTargetPackage() { return targetPackage; }
    public String getMessage() { return message; }
}
