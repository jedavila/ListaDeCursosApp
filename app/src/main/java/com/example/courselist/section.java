package com.example.courselist;

public class section {

    private String section="";
    private String days = "";
    private String start="";
    private String bdl="";
    private String instructor="";

    public section() {
    }

    public section(String section, String days, String start, String bdl, String rm, String instructor) {
        this.section = section;
        this.days = days;
        this.start = start;
        this.bdl = bdl+rm;
        this.instructor=instructor;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getBdl() {
        return bdl;
    }

    public void setBdl(String bdl) {
        this.bdl = bdl;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

}
