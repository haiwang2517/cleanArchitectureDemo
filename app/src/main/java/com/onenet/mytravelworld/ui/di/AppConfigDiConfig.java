package com.onenet.mytravelworld.ui.di;

import com.onenet.common.AppConfig;
import com.onenet.mytravelworld.BuildConfig;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

/**
 * AppConfigDiConfig
 *
 * @author HaiYinLong
 * @version 2025/05/26 16:10
 **/
@Module
@InstallIn(SingletonComponent.class)
public class AppConfigDiConfig {

    @Provides
    public AppConfig provideAppConfig() {
        return new AppConfig(BuildConfig.BASE_URL);
    }

}
