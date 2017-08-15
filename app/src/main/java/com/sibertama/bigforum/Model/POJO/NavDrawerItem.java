package com.sibertama.bigforum.Model.POJO;

import android.graphics.drawable.Drawable;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 4/26/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class NavDrawerItem {

    private boolean showNotify;
    private String title;
    private Drawable icon;


    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title , Drawable icon) {
        this.showNotify = showNotify;
        this.title = title;
        this.icon = icon;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public Drawable getIcon(){
        return icon;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
