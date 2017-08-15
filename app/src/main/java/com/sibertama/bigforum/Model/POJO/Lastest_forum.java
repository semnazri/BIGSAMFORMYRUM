package com.sibertama.bigforum.Model.POJO;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 10/31/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class Lastest_forum {


    String category_id, thread_id, thread_name, create_at, thread_comment, thread_like, author_name, category_name, thread_content;

    public Lastest_forum() {
    }

    public Lastest_forum(String category_id, String thread_id, String thread_name, String create_at, String thread_comment, String thread_like,
                         String author_name, String category_name, String thread_content) {
        this.category_id = category_id;
        this.thread_id = thread_id;
        this.thread_name = thread_name;
        this.create_at = create_at;
        this.thread_comment = thread_comment;
        this.thread_like = thread_like;
        this.author_name = author_name;
        this.category_name = category_name;
        this.thread_content = thread_content;

    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String id) {
        this.category_id = category_id;
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

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
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

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getThread_content() {
        return thread_content;
    }

    public void setThread_content(String thread_content) {
        this.thread_content = thread_content;
    }
}
