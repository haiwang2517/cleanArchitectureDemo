package com.onenet.mytravelworld.domain.model;

/**
 * Material
 *
 * @author HaiYinLong
 * @version 2025/05/21 18:29
 **/
public class Material {
    private Integer level;
    private Integer imageResourceId;

    public Material(Integer level, Integer imageResourceId) {
        this.level = level;
        this.imageResourceId = imageResourceId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(Integer imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
