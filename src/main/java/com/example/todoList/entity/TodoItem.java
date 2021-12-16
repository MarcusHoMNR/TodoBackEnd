package com.example.todoList.entity;

public class TodoItem {
    private String text;
    private String id;
    private Boolean done;

    public TodoItem(String id, String text, Boolean done) {
        this.text = text;
        this.id = id;
        this.done = done;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean isDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}
