package com.onenet.mytravelworld.ui.merge;

import com.onenet.domain.model.Cell;
import com.onenet.mytravelworld.R;

import android.view.View;
import android.widget.ImageView;
import androidx.databinding.BindingAdapter;

/**
 * MergeCellImageBindingAdapter
 *
 * @author HaiYinLong
 * @version 2025/06/03 14:41
 **/
public class MergeCellImageBindingAdapter {
    @BindingAdapter("materialResource")
    public static void setMaterialResource(ImageView view, Cell cell) {
        if (null != cell && cell.hasMaterial()) {
            view.setImageResource(cell.getMaterial().getImageResourceId());
            view.setVisibility(View.VISIBLE);
        } else {
            view.setImageResource(R.drawable.ic_launcher_background);
            view.setVisibility(View.VISIBLE);
        }
    }
}
