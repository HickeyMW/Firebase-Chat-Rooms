package com.wickeddevs.firebasechatrooms.dagger.module;

import com.wickeddevs.firebasechatrooms.data.service.contract.ChatRoomService;
import com.wickeddevs.firebasechatrooms.data.service.contract.NameService;
import com.wickeddevs.firebasechatrooms.data.service.contract.UserService;
import com.wickeddevs.firebasechatrooms.data.service.firebase.FbChatRoomService;
import com.wickeddevs.firebasechatrooms.data.service.firebase.FbInfo;
import com.wickeddevs.firebasechatrooms.data.service.firebase.SpNameService;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @Provides
    ChatRoomService providesChatRoomService() {
        return new FbChatRoomService();
    }

    @Provides
    UserService providesUserService() {
        return new FbInfo();
    }

    @Provides
    NameService providesNameService() {
        return new SpNameService();
    }
}
