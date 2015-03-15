package com.cartoonerie.app;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Maxime on 15/03/15.
 */
public class Project implements Serializable {
    private String name;
    private String uri;

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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
