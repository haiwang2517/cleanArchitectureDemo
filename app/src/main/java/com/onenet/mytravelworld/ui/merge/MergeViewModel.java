package com.onenet.mytravelworld.ui.merge;

import java.util.concurrent.ThreadLocalRandom;

import com.onenet.datasource.entity.DicGiftBoxConfigEntity;
import com.onenet.domain.model.Cell;
import com.onenet.domain.repository.DictionaryRepository;
import com.onenet.domain.repository.DomainCallback;
import com.onenet.mytravelworld.ui.UiToastCallback;
import com.onenet.mytravelworld.ui.common.MaterialFactory;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import jakarta.inject.Inject;

@HiltViewModel
public class MergeViewModel extends ViewModel {
    private final MutableLiveData<Cell[]> CELL_ARRAY = new MutableLiveData<>();
    private final DictionaryRepository dictionaryRepository;
    private final UiToastCallback uiCallback;

    @Inject
    public MergeViewModel(DictionaryRepository dictionaryRepository, UiToastCallback uiCallback) {
        this.dictionaryRepository = dictionaryRepository;
        this.uiCallback = uiCallback;

        CELL_ARRAY.setValue(new Cell[] {new Cell(0), new Cell(1), new Cell(2), new Cell(3), new Cell(4), new Cell(5),
            new Cell(6), new Cell(7), new Cell(8), new Cell(9), new Cell(10), new Cell(11)});
    }

    private int count = 0;

    public void addMaterial() {
        if (count > 11) {
            uiCallback.showShortToast("已经超过领取次数");
            return;
        }
        int level = ThreadLocalRandom.current().nextInt(1, 5);
        Cell[] value = CELL_ARRAY.getValue();
        Cell cell = value[count];
        cell.setMaterial(MaterialFactory.getMaterial(level));
        value[count] = cell;
        CELL_ARRAY.setValue(value);
        count++;
    }

    public LiveData<Cell[]> getCellArray() {
        return CELL_ARRAY;
    }

    public void handleMaterialSwap(Integer sourceIndex, Integer targetIndex) {
        Cell[] value = CELL_ARRAY.getValue();
        Cell sourceCell = value[sourceIndex];
        Cell targetCell = value[targetIndex];

        if (targetCell.hasMaterial() && sourceCell.hasMaterial()
            && sourceCell.getMaterial().getLevel().equals(targetCell.getMaterial().getLevel())) {
            // 合并, 删除两个素材，在目标位置添加一个新素材
            sourceCell.setMaterial(null);
            value[sourceIndex] = sourceCell;
            int newLevel = targetCell.getMaterial().getLevel() + 1;
            targetCell.setMaterial(MaterialFactory.getMaterial(newLevel));
            value[targetIndex] = targetCell;
        } else {
            // 移动
            value[sourceIndex] = targetCell;
            value[targetIndex] = sourceCell;
        }
        CELL_ARRAY.setValue(value);
    }

    public void getGiftConfig() {
        // 获取请求
        dictionaryRepository.queryGiftBoxConfig(new DomainCallback<>() {
            @Override
            public void onSuccess(DicGiftBoxConfigEntity config) {
                Log.i("Merge", "更新本次存储数据");
                uiCallback.showLongToast("更新本次存储数据");
            }

            @Override
            public void onFailure(String message) {
                Log.i("Merge", "数据字典更新异常");
                uiCallback.showShortToast("数据字典更新异常");
            }
        });
    }
}
