package com.onenet.domain.di;

import com.onenet.datasource.dao.DicDao;
import com.onenet.domain.repository.DictionaryRepository;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

/**
 * UserMaterialRepositoryDiModule
 *
 * @author HaiYinLong
 * @version 2025/05/22 17:58
 **/
@Module
@InstallIn(ViewModelComponent.class)
public class DictionaryRepositoryDiConfig {
    @Provides
    public DictionaryRepository provideUserMaterialRepository(DicDao dicDao) {
        return new DictionaryRepository(dicDao);
    }
}
