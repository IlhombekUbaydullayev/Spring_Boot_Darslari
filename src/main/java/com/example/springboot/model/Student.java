package com.example.springboot.model;

public class Student {

    private Long id;
    private String name;

    public Student(Long id, String name, String latName, String course) {
        this.id = id;
        this.name = name;
        this.latName = latName;
        this.course = course;
    }

    private String latName;
    private String course;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatName(String latName) {
        this.latName = latName;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLatName() {
        return latName;
    }

    public String getCourse() {
        return course;
    }
}
