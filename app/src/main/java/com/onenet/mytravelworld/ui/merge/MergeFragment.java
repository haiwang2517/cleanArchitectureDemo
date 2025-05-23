package com.onenet.mytravelworld.ui.merge;

import java.util.HashMap;
import java.util.Map;

import com.onenet.mytravelworld.R;
import com.onenet.mytravelworld.databinding.FragmentMergeBinding;

import android.os.Bundle;
import android.view.*;
import android.widget.GridLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MergeFragment extends Fragment {

    private FragmentMergeBinding binding;
    private MergeViewModel mergeViewModel;

    private Map<Integer, ImageView> cellImageViewMap;

    public MergeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mergeViewModel = new ViewModelProvider(this).get(MergeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        binding = FragmentMergeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        cellImageViewMap = initCellImageViewMap(binding);

        GridLayout gridLayout = binding.gridLayoutMerge;
        gridLayout.setOnDragListener((v, event) -> {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return true;
                case DragEvent.ACTION_DROP: // 拖放释放
                    View droppedView = (View)event.getLocalState();
                    float dropX = event.getX();
                    float dropY = event.getY();
                    // 查找目标视图
                    ImageView targetView = findViewAtPosition(gridLayout, dropX, dropY);
                    if (targetView != null && !droppedView.equals(targetView)) {
                        Integer sourceIndex = Integer.parseInt((String)droppedView.getTag());
                        Integer targetIndex = Integer.parseInt((String)targetView.getTag());
                        mergeViewModel.handleMaterialSwap(sourceIndex, targetIndex);
                        // TODO 目标添加特效
                    }
                    return true;

                case DragEvent.ACTION_DRAG_ENDED: // 拖拽结束
                    return true;

                default:
                    return false;
            }
        });

        // 根据vm中的数据给4x3规格的所有格子添加观察
        mergeViewModel.getCellArray().observe(getViewLifecycleOwner(), cells -> {
            // 遍历所有格子; cell和各个格子比较有数据就显示
            cellImageViewMap.forEach((index, imageView) -> {
                if (cells[index].hasMaterial()) {
                    imageView.setImageResource(cells[index].getMaterial().getImageResourceId());
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setImageResource(R.drawable.ic_launcher_background);
                }
            });
        });

        // button 添加监听,点击一次增加一个素材
        binding.buttonMerge.setOnClickListener(v -> mergeViewModel.addMaterial());
        binding.buttonGetDic.setOnClickListener(v -> mergeViewModel.getGiftConfig());
        // 监控一个图片移动到另一个图片上
        cellImageViewMap.forEach((index, imageView) -> imageView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDragAndDrop(null, shadowBuilder, v, 0);
                return true;
            }
            return false;
        }));
        // 切换后数据丢失
        return root;
    }

    private ImageView findViewAtPosition(ViewGroup parent, float x, float y) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            ImageView child = (ImageView)parent.getChildAt(i);
            if (isPointInView(child, x, y)) {
                return child;
            }
        }
        return null;
    }

    private boolean isPointInView(View view, float x, float y) {
        int left = view.getLeft();
        int top = view.getTop();
        int right = left + view.getWidth();
        int bottom = top + view.getHeight();
        return x >= left && x <= right && y >= top && y <= bottom;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private Map<Integer, ImageView> initCellImageViewMap(FragmentMergeBinding binding) {
        Map<Integer, ImageView> initCellImageViewMap = new HashMap<>();
        initCellImageViewMap.put(1, binding.imageView0);
        initCellImageViewMap.put(2, binding.imageView1);
        initCellImageViewMap.put(3, binding.imageView2);
        initCellImageViewMap.put(4, binding.imageView3);
        initCellImageViewMap.put(5, binding.imageView4);
        initCellImageViewMap.put(6, binding.imageView5);
        initCellImageViewMap.put(7, binding.imageView6);
        initCellImageViewMap.put(8, binding.imageView7);
        initCellImageViewMap.put(9, binding.imageView8);
        initCellImageViewMap.put(10, binding.imageView9);
        initCellImageViewMap.put(11, binding.imageView10);
        initCellImageViewMap.put(12, binding.imageView11);
        return initCellImageViewMap;
    }

}
