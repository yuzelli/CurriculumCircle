package com.example.yuzelli.curriculumcircle.Bean;

import java.io.Serializable;

/**
 * Created by 51644 on 2017/5/9.
 */

public class TieZi implements Serializable {
    private Long time;
    private String title;

    public TieZi(Long time, String title) {
        this.time = time;
        this.title = title;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
