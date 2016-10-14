package com.wickeddevs.firebasechatrooms.ui.home;

import java.util.ArrayList;

public interface HomeContract {

    interface View {

        void displayChatRooms(ArrayList<String> chatRoomNames);

        void displayChatRoomUi(String chatRoomName, String username);

        void displayChangeNameUi(String username);
    }

    interface Presenter {

        void viewCreated(View createdView);

        void selectedRoom(String chatRoomName);

        void selectedChangeName();

        void changeName(String username);
    }
}
