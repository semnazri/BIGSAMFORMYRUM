package com.sibertama.bigforum.Model.POJO;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 6/2/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class Forum {
    String cat_id;
    String cat_name;
    String last_reply;
    String jml_post;

    public Forum(){

    }

    public Forum(String cat_id, String cat_name, String last_reply, String jml_post){
        this.cat_id = cat_id;
        this.cat_name = cat_name;
        this.last_reply  = last_reply;
        this.jml_post = jml_post;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getLast_reply() {
        return last_reply;
    }

    public void setLast_reply(String last_reply) {
        this.last_reply = last_reply;
    }

    public String getJml_post() {
        return jml_post;
    }

    public void setJml_post(String jml_post) {
        this.jml_post = jml_post;
    }
}
