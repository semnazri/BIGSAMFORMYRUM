package com.sibertama.bigforum.Model.POJO;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 10/27/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class Lastest_Status {

    String id,status_content,status_like,create_at,condition,author_lastest_status, url_img;

    public Lastest_Status(){
    }

    public Lastest_Status(String url_img,String id, String status_content, String status_like,String create_at, String condition, String author_lastest_status){
        this.id = id;
        this.status_content = status_content;
        this.status_like = status_like;
        this.create_at = create_at;
        this.condition = condition;
        this.author_lastest_status = author_lastest_status;
        this.url_img = url_img;
    }

    public String getAuthor_lastest_status() {
        return author_lastest_status;
    }

    public void setAuthor_lastest_status(String author_lastest_status) {
        this.author_lastest_status = author_lastest_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus_content() {
        return status_content;
    }

    public void setStatus_content(String status_content) {
        this.status_content = status_content;
    }

    public String getStatus_like() {
        return status_like;
    }

    public void setStatus_like(String status_like) {
        this.status_like = status_like;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public String getUrl_img() {
        return url_img;
    }
}
