package com.jzh.basemodule.bean;

public abstract class FileBean {
    public static final int TYPE_PICTURE = 0;
    public static final int TYPE_PDF = 1;
    public static final int TYPE_HTML = 2;

    protected String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
