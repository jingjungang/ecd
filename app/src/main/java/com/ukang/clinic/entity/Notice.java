package com.ukang.clinic.entity;

/**
 * Created by AAA on 2016/7/7.
 */
public class Notice {

    /**公告ID*/
    private String id;
    /**用户ID*/
    private String userId;
    /**公告添加时间*/
    private String add_time;
    /**公告修改时间*/
    private String update_time;
    /**公告类型*/
    private int type;
    /**公告标题*/
    private String title;
    /**公告状态*/
    private int state;
    /**公告附件*/
    private String thumb;
    /**公告作者*/
    private String username;
    /**公告作者名*/
    private String nickname;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public String getId() {
        return id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public int getState() {
        return state;
    }

    public String getThumb() {
        return thumb;
    }

    public String getUsername() {
        return username;
    }
}
