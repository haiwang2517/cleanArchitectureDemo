package com.onenet.mytravelworld.domain.model;

/**
 * Cell
 *
 * @author HaiYinLong
 * @version 2025/05/22 09:26
 **/
public class Cell {
    private Integer index;
    private Material material;

    public Cell(Integer index) {
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public boolean hasMaterial() {
        return material != null;
    }
}
