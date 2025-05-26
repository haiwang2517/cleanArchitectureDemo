package com.onenet.datasource.di;

import com.onenet.datasource.dao.DicDao;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import retrofit2.Retrofit;

/**
 * DicDaoDiModule
 *
 * @author HaiYinLong
 * @version 2025/05/23 12:02
 **/
@Module
@InstallIn(ViewModelComponent.class)
public class DicDaoDiModule {
    @Provides
    public DicDao provideDicDao(Retrofit retrofit) {
        return retrofit.create(DicDao.class);
    }
}
