package com.sibertama.bigforum.Model.POJO;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 7/21/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class Download {

    String DOWNLOAD_ID, DOWNLOAD_NAME, DOWNLOAD_FILE, NAMA, NAMA_BAGIAN, FILE_TYPE, CREATED_AT ;

    public Download(){

    }

    public Download(String DOWNLOAD_ID, String DOWNLOAD_NAME, String DOWNLOAD_FILE, String NAMA, String NAMA_BAGIAN, String FILE_TYPE, String CREATED_AT){
        this.DOWNLOAD_ID = DOWNLOAD_ID;
        this.DOWNLOAD_NAME = DOWNLOAD_NAME;
        this.DOWNLOAD_FILE = DOWNLOAD_FILE;
        this.NAMA = NAMA;
        this.NAMA_BAGIAN = NAMA_BAGIAN;
        this.FILE_TYPE = FILE_TYPE;
        this.CREATED_AT = CREATED_AT;
    }

    public void setCREATED_AT(String CREATED_AT) {
        this.CREATED_AT = CREATED_AT;
    }

    public String getCREATED_AT() {
        return CREATED_AT;
    }

    public void setDOWNLOAD_FILE(String DOWNLOAD_FILE) {
        this.DOWNLOAD_FILE = DOWNLOAD_FILE;
    }

    public String getDOWNLOAD_FILE() {
        return DOWNLOAD_FILE;
    }

    public void setDOWNLOAD_ID(String DOWNLOAD_ID) {
        this.DOWNLOAD_ID = DOWNLOAD_ID;
    }

    public String getDOWNLOAD_ID() {
        return DOWNLOAD_ID;
    }

    public void setDOWNLOAD_NAME(String DOWNLOAD_NAME) {
        this.DOWNLOAD_NAME = DOWNLOAD_NAME;
    }

    public String getDOWNLOAD_NAME() {
        return DOWNLOAD_NAME;
    }

    public void setFILE_TYPE(String FILE_TYPE) {
        this.FILE_TYPE = FILE_TYPE;
    }

    public String getFILE_TYPE() {
        return FILE_TYPE;
    }

    public void setNAMA(String NAMA) {
        this.NAMA = NAMA;
    }

    public String getNAMA() {
        return NAMA;
    }

    public void setNAMA_BAGIAN(String NAMA_BAGIAN) {
        this.NAMA_BAGIAN = NAMA_BAGIAN;
    }

    public String getNAMA_BAGIAN() {
        return NAMA_BAGIAN;
    }

}
