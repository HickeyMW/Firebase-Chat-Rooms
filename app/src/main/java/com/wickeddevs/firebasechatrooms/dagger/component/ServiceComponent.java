package com.wickeddevs.firebasechatrooms.dagger.component;

import com.wickeddevs.firebasechatrooms.dagger.module.ServiceModule;
import com.wickeddevs.firebasechatrooms.data.service.contract.ChatRoomService;
import com.wickeddevs.firebasechatrooms.data.service.contract.NameService;
import com.wickeddevs.firebasechatrooms.data.service.contract.UserService;
import com.wickeddevs.firebasechatrooms.data.service.firebase.SpNameService;

import dagger.Component;

@Component(modules = ServiceModule.class)
public interface ServiceComponent {

    ChatRoomService providesChatRoomService();

    UserService providesUserService();

    NameService providesNameService();
}
