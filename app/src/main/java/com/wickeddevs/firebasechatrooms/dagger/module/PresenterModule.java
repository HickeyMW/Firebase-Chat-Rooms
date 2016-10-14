package com.wickeddevs.firebasechatrooms.dagger.module;

import com.wickeddevs.firebasechatrooms.dagger.scope.ActivityScope;
import com.wickeddevs.firebasechatrooms.ui.home.HomeContract;
import com.wickeddevs.firebasechatrooms.ui.home.HomePresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    @ActivityScope
    HomeContract.Presenter providesHomePresenter(HomePresenter homePresenter) {
        return homePresenter;
    }
}
