package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/18.
 */

public class LinkageTemplate {
    String id;
    String name;
    String scriptType;
    String script;
    String comment;

    @Override
    public String toString() {
        return "LinkageTemplate{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", scriptType='" + scriptType + '\'' +
                ", script='" + script + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScriptType() {
        return scriptType;
    }

    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LinkageTemplate() {

    }

    public LinkageTemplate(String id, String name, String scriptType, String script, String comment) {

        this.id = id;
        this.name = name;
        this.scriptType = scriptType;
        this.script = script;
        this.comment = comment;
    }
}
