package com.sibertama.bigforum.Model.POJO;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 6/29/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class Gallery {

    String gallery_id;
    String gallery_name;
    String gallery_comment;
    String gallery_file;

    public Gallery(){

    }

    public Gallery (String gallery_id, String gallery_name, String gallery_comment, String gallery_file){
        this.gallery_id = gallery_id;
        this.gallery_name = gallery_name;
        this.gallery_comment = gallery_comment;
        this.gallery_file = gallery_file;
    }

    public String getGallery_id() {
        return gallery_id;
    }

    public void setGallery_id(String gallery_id) {
        this.gallery_id = gallery_id;
    }

    public String getGallery_name() {
        return gallery_name;
    }

    public void setGallery_name(String gallery_name) {
        this.gallery_name = gallery_name;
    }

    public String getGallery_comment() {
        return gallery_comment;
    }

    public void setGallery_comment(String gallery_comment) {
        this.gallery_comment = gallery_comment;
    }

    public String getGallery_file() {
        return gallery_file;
    }

    public void setGallery_file(String gallery_file) {
        this.gallery_file = gallery_file;
    }
}
