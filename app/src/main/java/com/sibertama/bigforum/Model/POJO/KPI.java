package com.sibertama.bigforum.Model.POJO;

import java.lang.annotation.Target;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 6/16/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class KPI {
    String KETERANGAN_SUBSUBDET;
    String BOBOT;
    String TARGET;
    String KET;
    String REALISASI;
    String PENCAPAIAN;
    String PERINGKAT;
    String NILAI;

    public KPI(){

    }

    public KPI(String KETERANGAN_SUBSUBDET, String BOBOT, String TARGET, String KET, String REALISASI, String PENCAPAIAN, String PERINGKAT, String NILAI){

        this.KETERANGAN_SUBSUBDET = KETERANGAN_SUBSUBDET;
        this.BOBOT = BOBOT;
        this.TARGET = TARGET;
        this.KET = KET;
        this.REALISASI = REALISASI;
        this.PENCAPAIAN = PENCAPAIAN;
        this.PERINGKAT = PERINGKAT;
        this.NILAI = NILAI;
    }

    public void setKETERANGAN_SUBSUBDET(String KETERANGAN_SUBSUBDET) {
        this.KETERANGAN_SUBSUBDET = KETERANGAN_SUBSUBDET;
    }

    public String getKETERANGAN_SUBSUBDET() {
        return KETERANGAN_SUBSUBDET;
    }

    public void setBOBOT(String BOBOT) {
        this.BOBOT = BOBOT;
    }

    public String getBOBOT() {
        return BOBOT;
    }

    public void setTARGET(String TARGET) {
        this.TARGET = TARGET;
    }

    public String getTARGET() {
        return TARGET;
    }

    public void setKET(String KET) {
        this.KET = KET;
    }

    public String getKET() {
        return KET;
    }

    public void setREALISASI(String REALISASI) {
        this.REALISASI = REALISASI;
    }

    public String getREALISASI() {
        return REALISASI;
    }

    public void setPENCAPAIAN(String PENCAPAIAN) {
        this.PENCAPAIAN = PENCAPAIAN;
    }

    public String getPENCAPAIAN() {
        return PENCAPAIAN;
    }

    public void setPERINGKAT(String PERINGKAT) {
        this.PERINGKAT = PERINGKAT;
    }

    public String getPERINGKAT() {
        return PERINGKAT;
    }

    public void setNILAI(String NILAI) {
        this.NILAI = NILAI;
    }

    public String getNILAI() {
        return NILAI;
    }
}
