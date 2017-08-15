package com.sibertama.bigforum.Model.POJO;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 9/21/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class Izin {

    private String id,izin_desc,izin_hari;

    public Izin(){
    }

    public Izin (String id, String izin_desc, String izin_hari){
        this.id = id;
        this.izin_desc = izin_desc;
        this.izin_hari = izin_hari;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIzin_desc() {
        return izin_desc;
    }

    public void setIzin_desc(String izin_desc) {
        this.izin_desc = izin_desc;
    }

    public String getIzin_hari() {
        return izin_hari;
    }

    public void setIzin_hari(String izin_hari) {
        this.izin_hari = izin_hari;
    }
}
