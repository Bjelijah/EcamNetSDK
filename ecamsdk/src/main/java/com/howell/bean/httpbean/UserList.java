package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/6.
 */

public class UserList {

    Page page;
    ArrayList<User> users;

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public UserList(Page page, ArrayList<User> users) {
        this.page = page;
        this.users = users;
    }

    public UserList() {
    }

    @Override
    public String toString() {
        return "UserList{" +
                "page=" + page +
                ", users=" + users +
                '}';
    }


}
