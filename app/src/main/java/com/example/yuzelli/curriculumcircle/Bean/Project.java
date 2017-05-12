package com.example.yuzelli.curriculumcircle.Bean;

import java.io.Serializable;

/**
 * Created by 51644 on 2017/5/9.
 */

public class Project implements Serializable {
    private String name;
    private String content;
    private String time;
    private String teacher;
    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }



    public Project(String name, String content, String time,String teacher) {
        this.name = name;
        this.content = content;
        this.time = time;
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
