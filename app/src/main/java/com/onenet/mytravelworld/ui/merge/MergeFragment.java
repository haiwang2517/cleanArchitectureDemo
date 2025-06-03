package com.onenet.mytravelworld.ui.merge;

import com.onenet.mytravelworld.R;
import com.onenet.mytravelworld.databinding.FragmentMergeBinding;

import android.os.Bundle;
import android.view.*;
import android.widget.GridLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MergeFragment extends Fragment {

    private FragmentMergeBinding binding;
    private MergeViewModel mergeViewModel;

    public MergeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mergeViewModel = new ViewModelProvider(this).get(MergeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_merge, container, false);
        // 设置ViewModel
        bandingViewModel();
        // 图片组件添加触摸监听
        setupCellImageTouchListener();
        // Grid添加拖动效果
        setupGridLayoutDragListener();

        return binding.getRoot();
    }

    private void bandingViewModel() {
        binding.setViewModel(mergeViewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
    }

    private void setupGridLayoutDragListener() {
        GridLayout gridLayout = binding.gridLayoutMerge;
        gridLayout.setOnDragListener((v, event) -> {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return true;
                case DragEvent.ACTION_DROP: // 拖放释放
                    handleDropEvent(event, gridLayout);
                    return true;
                case DragEvent.ACTION_DRAG_ENDED: // 拖拽结束
                    return true;
                default:
                    return false;
            }
        });
    }

    private void handleDropEvent(DragEvent event, GridLayout gridLayout) {
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
    }

    private void setupCellImageTouchListener() {
        cellImageViewAddTouchListener(binding.imageView0);
        cellImageViewAddTouchListener(binding.imageView1);
        cellImageViewAddTouchListener(binding.imageView2);
        cellImageViewAddTouchListener(binding.imageView3);
        cellImageViewAddTouchListener(binding.imageView4);
        cellImageViewAddTouchListener(binding.imageView5);
        cellImageViewAddTouchListener(binding.imageView6);
        cellImageViewAddTouchListener(binding.imageView7);
        cellImageViewAddTouchListener(binding.imageView8);
        cellImageViewAddTouchListener(binding.imageView9);
        cellImageViewAddTouchListener(binding.imageView10);
        cellImageViewAddTouchListener(binding.imageView11);
    }

    private void cellImageViewAddTouchListener(ImageView imageView) {
        imageView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDragAndDrop(null, shadowBuilder, v, 0);
                return true;
            }
            return false;
        });
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

}
