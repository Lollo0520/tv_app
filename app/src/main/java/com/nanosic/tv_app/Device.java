package com.nanosic.tv_app;

import android.support.annotation.DrawableRes;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/12.
 */
public class Device implements Serializable{

    private @DrawableRes int image;
    private String title;
    private boolean isSetUp = false;

    public Device(){
    }

    public @DrawableRes int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setImage(@DrawableRes int image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSetUp() {
        return isSetUp;
    }

    public void setUp(boolean setUp) {
        isSetUp = setUp;
    }
}
