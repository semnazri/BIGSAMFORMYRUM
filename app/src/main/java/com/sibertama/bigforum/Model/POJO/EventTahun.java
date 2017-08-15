package com.sibertama.bigforum.Model.POJO;

/**
 * Created by semmy on 7/24/2016.
 */
public class EventTahun {

    private String id;
    private String tahun;

    public EventTahun(){

    }

    public EventTahun(String id, String tahun){
        this.id = id;
        this.tahun = tahun;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getTahun() {
        return tahun;
    }
}
