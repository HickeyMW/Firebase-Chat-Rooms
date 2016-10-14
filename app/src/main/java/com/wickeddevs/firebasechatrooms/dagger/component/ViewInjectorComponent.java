package com.wickeddevs.firebasechatrooms.dagger.component;

import com.wickeddevs.firebasechatrooms.dagger.module.PresenterModule;
import com.wickeddevs.firebasechatrooms.dagger.scope.ActivityScope;
import com.wickeddevs.firebasechatrooms.ui.home.HomeActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = {ServiceComponent.class}, modules = PresenterModule.class)
public interface ViewInjectorComponent {

    void inject(HomeActivity homeActivity);
}
