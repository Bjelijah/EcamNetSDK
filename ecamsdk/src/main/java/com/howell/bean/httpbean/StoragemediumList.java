package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/13.
 */

public class StoragemediumList {
    Page page;
    ArrayList<StorageMedium> storageMedias;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<StorageMedium> getStorageMedias() {
        return storageMedias;
    }

    public void setStorageMedias(ArrayList<StorageMedium> storageMedias) {
        this.storageMedias = storageMedias;
    }

    public StoragemediumList(Page page, ArrayList<StorageMedium> storageMedias) {
        this.page = page;
        this.storageMedias = storageMedias;
    }

    public StoragemediumList() {
    }

    @Override
    public String toString() {
        return "StoragemediumList{" +
                "page=" + page +
                ", storageMedias=" + storageMedias +
                '}';
    }
}
