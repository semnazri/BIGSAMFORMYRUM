package com.sibertama.bigforum.Model.POJO;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 6/6/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class Penilaian {
    String employ_name;
    String periode;
    String acumulate_score;
    String text_score;
    String nama_penilai1;
    String nilai_penilai1;
    String text_penilai1;
    String nama_penilai2;
    String nilai_penilai2;
    String text_penilai2;
    String nama_penilai3;
    String nilai_penilai3;
    String text_penilai3;


    public Penilaian(){

    }

    public Penilaian(String employ_name, String periode, String acumulate_score, String text_score, String nama_penilai1, String nilai_penilai1, String text_penilai1,
                     String nama_penilai2, String nilai_penilai2, String text_penilai2,String nama_penilai3, String nilai_penilai3, String text_penilai3  ){
        this.employ_name = employ_name;
        this.periode = periode;
        this.acumulate_score  = acumulate_score;
        this.text_score = text_score;

        this.nama_penilai1 = nama_penilai1;
        this.nilai_penilai1  = nilai_penilai1;
        this.text_penilai1 = text_penilai1;

        this.nama_penilai2 = nama_penilai2;
        this.nilai_penilai2  = nilai_penilai2;
        this.text_penilai2 = text_penilai2;

        this.nama_penilai3 = nama_penilai3;
        this.nilai_penilai3  = nilai_penilai3;
        this.text_penilai3 = text_penilai3;
    }

    public void setEmploy_name(String employ_name) {
        this.employ_name = employ_name;
    }

    public String getEmploy_name() {
        return employ_name;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public String getPeriode() {
        return periode;
    }

    public void setAcumulate_score(String acumulate_score) {
        this.acumulate_score = acumulate_score;
    }

    public String getAcumulate_score() {
        return acumulate_score;
    }

    public void setText_score(String text_score) {
        this.text_score = text_score;
    }

    public String getText_score() {
        return text_score;
    }


    public void setNama_penilai1(String nama_penilai1) {
        this.nama_penilai1 = nama_penilai1;
    }

    public String getNama_penilai1() {
        return nama_penilai1;
    }

    public void setNilai_penilai1(String nilai_penilai1) {
        this.nilai_penilai1 = nilai_penilai1;
    }

    public String getNilai_penilai1() {
        return nilai_penilai1;
    }

    public void setText_penilai1(String text_penilai1) {
        this.text_penilai1 = text_penilai1;
    }

    public String getText_penilai1() {
        return text_penilai1;
    }

    public void setNama_penilai2(String nama_penilai2) {
        this.nama_penilai2 = nama_penilai2;
    }

    public String getNama_penilai2() {
        return nama_penilai2;
    }

    public void setNilai_penilai2(String nilai_penilai2) {
        this.nilai_penilai2 = nilai_penilai2;
    }

    public String getNilai_penilai2() {
        return nilai_penilai2;
    }

    public void setText_penilai2(String text_penilai2) {
        this.text_penilai2 = text_penilai2;
    }

    public String getText_penilai2() {
        return text_penilai2;
    }

    public void setNama_penilai3(String nama_penilai3) {
        this.nama_penilai3 = nama_penilai3;
    }

    public String getNama_penilai3() {
        return nama_penilai3;
    }

    public void setNilai_penilai3(String nilai_penilai3) {
        this.nilai_penilai3 = nilai_penilai3;
    }

    public String getNilai_penilai3() {
        return nilai_penilai3;
    }

    public void setText_penilai3(String text_penilai3) {
        this.text_penilai3 = text_penilai3;
    }

    public String getText_penilai3() {
        return text_penilai3;
    }
}
