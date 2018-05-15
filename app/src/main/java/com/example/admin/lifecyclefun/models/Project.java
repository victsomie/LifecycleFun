package com.example.admin.lifecyclefun.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 2018-05-14.
 */

public class Project {
//
//    @PrimaryKey
//    private int uid;

    //@PrimaryKey
    //@ColumnInfo(name = "id")
    public long id;

    //@ColumnInfo(name = "name")
    @SerializedName("name")
    public String name;

    //@ColumnInfo(name = "full_name")
    @SerializedName("full_name")
    public String full_name;
    //public User owner;
    public String html_url;
    public String description;
    public String url;
    //public Date created_at;
    //public Date updated_at;
    //public Date pushed_at;
    public String git_url;
    public String ssh_url;
    public String clone_url;
    public String svn_url;
    public String homepage;
    public int stargazers_count;
    public int watchers_count;
    public String language;
    public boolean has_issues;
    public boolean has_downloads;
    public boolean has_wiki;
    public boolean has_pages;
    public int forks_count;
    public int open_issues_count;
    public int forks;
    public int open_issues;
    public int watchers;
    public String default_branch;

    public Project() {
    }

    /*
    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    */
    public Project(String name) {
        this.name = name;
    }
}