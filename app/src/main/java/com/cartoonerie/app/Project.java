package com.cartoonerie.app;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Maxime on 15/03/15.
 */
public class Project implements Serializable {
    private long id;
    private String name;
    private String videoPath;
    private int fps;

    public Project(String name) {
        this.name = name;
    }

    public Project() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
