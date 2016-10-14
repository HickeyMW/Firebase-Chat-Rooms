package com.wickeddevs.firebasechatrooms.ui.home;

import com.wickeddevs.firebasechatrooms.data.service.contract.ChatRoomService;
import com.wickeddevs.firebasechatrooms.data.service.contract.NameService;
import com.wickeddevs.firebasechatrooms.data.service.contract.UserService;

import java.util.ArrayList;

import javax.inject.Inject;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;
    private ChatRoomService chatRoomService;
    private UserService userService;
    private NameService nameService;

    @Inject
    public HomePresenter(ChatRoomService chatRoomService, UserService userService, NameService nameService) {
        this.chatRoomService = chatRoomService;
        this.userService = userService;
        this.nameService = nameService;
    }

    @Override
    public void viewCreated(HomeContract.View createdView) {
        view = createdView;
        userService.logInIfNotLoggedIn();
        chatRoomService.getChatRoomNames(new ChatRoomService.ChatRoomNamesCallback() {
            @Override
            public void onLoaded(ArrayList<String> chatRoomNames) {
                view.displayChatRooms(chatRoomNames);
            }
        });
    }

    @Override
    public void selectedRoom(String chatRoomName) {
        String username = nameService.getUsername();
        if (username == null) {
            view.displayChangeNameUi(username);
        } else {
            view.displayChatRoomUi(chatRoomName, username);
        }
    }

    @Override
    public void selectedChangeName() {
        String username = nameService.getUsername();
        if (username == null) {
            username = "";
        }
        view.displayChangeNameUi(username);
    }

    @Override
    public void changeName(String username) {
        nameService.setUserName(username);
    }
}
