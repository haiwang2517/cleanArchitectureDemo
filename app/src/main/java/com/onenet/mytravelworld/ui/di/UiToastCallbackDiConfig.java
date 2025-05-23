package com.onenet.mytravelworld.ui.di;

import com.onenet.mytravelworld.ui.UiToastCallback;

import android.content.Context;
import android.widget.Toast;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

/**
 * UiToastCallbackDiConfig
 *
 * @author HaiYinLong
 * @version 2025/05/23 16:15
 **/
@Module
@InstallIn(ViewModelComponent.class)
public class UiToastCallbackDiConfig {

    @Provides
    public UiToastCallback provideUiToastCallback(@ApplicationContext Context context) {
        return new UiToastCallback() {
            @Override
            public void showShortToast(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void showLongToast(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        };
    }
}
