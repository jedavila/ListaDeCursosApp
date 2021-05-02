package com.example.courselist;

public class Course {

    private String course = "";
    private String title="";
    private String credits="";
    private String level="";
    private String restrictions="";

    public Course() {
    }

    public Course(String course, String title, String credits, String level, String restrictions) {
        this.course = course;
        this.title = title;
        this.credits = credits;
        this.level = level;
        this.restrictions = restrictions.replace(";", "\n");
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

}
