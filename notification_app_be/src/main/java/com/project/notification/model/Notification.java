package com.project.notification.model;

public class Notification {

    private String id;
    private String type;
    private String message;
    private String recipient;

    public Notification() {}

    public Notification(String id, String type, String message, String recipient) {
        this.id = id;
        this.type = type;
        this.message = message;
        this.recipient = recipient;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }
}