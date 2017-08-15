package com.sibertama.bigforum.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 4/27/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class Category implements Serializable {

    private long id;
    private String name;
//    private String descr;

    private List<ItemDetail> itemList = new ArrayList<ItemDetail>();

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
//        this.descr = descr;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getDescr() {
//        return descr;
//    }
//
//    public void setDescr(String descr) {
//        this.descr = descr;
//    }

    public List<ItemDetail> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemDetail> itemList) {
        this.itemList = itemList;
    }

}
