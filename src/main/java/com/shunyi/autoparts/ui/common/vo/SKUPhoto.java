package com.shunyi.autoparts.ui.common.vo;

/**
 * @description SKU图片VO
 * @author Shunyi Chen
 * @date 2020/4/2
 */
public class SKUPhoto {
    /** 自增长ID */
    private Long id;
    /** 图片名称 */
    private String name;
    /** 图片路径 */
    private String path;
    /** 图片所在VFS文件系统 */
    private VFS vfs;

    public SKUPhoto() {}

    public SKUPhoto(Long id, String name, String path, VFS vfs) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.vfs = vfs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public VFS getVfs() {
        return vfs;
    }

    public void setVfs(VFS vfs) {
        this.vfs = vfs;
    }
}
