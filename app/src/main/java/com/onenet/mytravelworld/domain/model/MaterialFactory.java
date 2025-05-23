package com.onenet.mytravelworld.domain.model;

import java.util.HashMap;
import java.util.Map;

import com.onenet.mytravelworld.R;

/**
 * MaterialUtil
 *
 * @author HaiYinLong
 * @version 2025/05/22 09:32
 **/
public class MaterialFactory {
    // 获取随材及素材对应的图片资源
    private static final Map<Integer, Material> MATERIAL_RESOURCE_MAP = new HashMap<>() {
        {
            put(1, new Material(1, R.drawable.ic_dog_level1));
            put(2, new Material(2, R.drawable.ic_dog_level2));
            put(3, new Material(3, R.drawable.ic_dog_level3));
            put(4, new Material(4, R.drawable.ic_dog_level4));
            put(5, new Material(5, R.drawable.ic_dog_level5));
            put(6, new Material(6, R.drawable.ic_dog_level6));
            put(7, new Material(7, R.drawable.ic_dog_level7));
            put(8, new Material(8, R.drawable.ic_dog_level8));
            put(9, new Material(9, R.drawable.ic_dog_level9));
            put(10, new Material(10, R.drawable.ic_dog_level10));
            put(11, new Material(11, R.drawable.ic_dog_level11));
            put(12, new Material(12, R.drawable.ic_dog_level12));
            put(13, new Material(13, R.drawable.ic_dog_level13));
            put(14, new Material(14, R.drawable.ic_dog_level14));
            put(15, new Material(15, R.drawable.ic_dog_level15));
            put(16, new Material(16, R.drawable.ic_dog_level16));
            put(17, new Material(17, R.drawable.ic_dog_level17));
            put(18, new Material(18, R.drawable.ic_dog_level18));
            put(19, new Material(19, R.drawable.ic_dog_level19));
        }
    };

    public static Material getMaterial(Integer level) {
        return MATERIAL_RESOURCE_MAP.get(level);
    }
}
