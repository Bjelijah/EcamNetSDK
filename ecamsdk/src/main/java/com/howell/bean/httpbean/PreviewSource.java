package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/14.
 */

public class PreviewSource {
    String id;
    String previewFromId;
    String previewFromUrl;

    @Override
    public String toString() {
        return "PreviewSource{" +
                "id='" + id + '\'' +
                ", previewFromId='" + previewFromId + '\'' +
                ", previewFromUrl='" + previewFromUrl + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPreviewFromId() {
        return previewFromId;
    }

    public void setPreviewFromId(String previewFromId) {
        this.previewFromId = previewFromId;
    }

    public String getPreviewFromUrl() {
        return previewFromUrl;
    }

    public void setPreviewFromUrl(String previewFromUrl) {
        this.previewFromUrl = previewFromUrl;
    }

    public PreviewSource() {

    }

    public PreviewSource(String id, String previewFromId, String previewFromUrl) {

        this.id = id;
        this.previewFromId = previewFromId;
        this.previewFromUrl = previewFromUrl;
    }
}
