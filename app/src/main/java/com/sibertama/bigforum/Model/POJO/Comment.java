package com.sibertama.bigforum.Model.POJO;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 5/31/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class Comment {
    String thread_id;
    String thread_det_id;
    String thread_comment;
    String create_by;
    String create_at;
    String nip;
    String nama;
    String like;

    public Comment(){

    }

    public Comment(String thread_id, String thread_det_id, String thread_comment, String create_by, String create_at, String nip, String nama, String like){
        this.thread_id = thread_id;
        this.thread_det_id = thread_det_id;
        this.thread_comment  = thread_comment;
        this.create_by = create_by;
        this.create_at = create_at;
        this.nip = nip;
        this.nama = nama;
        this.like = like;
    }

    public String getThread_id(){
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public String getThread_det_id(){
        return thread_det_id;
    }

    public void setThread_det_id(String thread_det_id) {
        this.thread_det_id = thread_det_id;
    }

    public String getThread_comment() {
        return thread_comment;
    }

    public void setThread_comment(String thread_comment) {
        this.thread_comment = thread_comment;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setLike( String like){
        this.like = like;
    }

    public String getLike() {
        return like;
    }
}
