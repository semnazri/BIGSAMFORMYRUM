package com.sibertama.bigforum.Model.POJO;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 6/9/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class ForumDetail {

    String cat_id_det;
    String thread_id;
    String thread_name;
    String created_by;
    String created_at;
    String thread_comment;
    String thread_like;
    String name;
    String cat_name_det;
    String last_replay;

    public ForumDetail(){

    }

    public ForumDetail (String cat_id_det, String thread_id, String thread_name, String created_by, String created_at, String thread_comment,String thread_like, String name, String cat_name_det, String last_replay ){
        this.cat_id_det = cat_id_det;
        this.thread_id = thread_id;
        this.thread_name = thread_name;
        this.created_by = created_by;
        this.created_at = created_at;
        this.thread_comment = thread_comment;
        this.thread_like = thread_like;
        this.name = name;
        this.cat_name_det = cat_name_det;
        this.last_replay = last_replay;
    }

    public String getCat_id_det() {
        return cat_id_det;
    }

    public void setCat_id_det(String cat_id_det) {
        this.cat_id_det = cat_id_det;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public String getThread_name() {
        return thread_name;
    }

    public void setThread_name(String thread_name) {
        this.thread_name = thread_name;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getThread_comment() {
        return thread_comment;
    }

    public void setThread_comment(String thread_comment) {
        this.thread_comment = thread_comment;
    }

    public String getThread_like() {
        return thread_like;
    }

    public void setThread_like(String thread_like) {
        this.thread_like = thread_like;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCat_name_det() {
        return cat_name_det;
    }

    public void setCat_name_det(String cat_name_det) {
        this.cat_name_det = cat_name_det;
    }

    public String getLast_replay() {
        return last_replay;
    }

    public void setLast_replay(String last_replay) {
        this.last_replay = last_replay;
    }
}
