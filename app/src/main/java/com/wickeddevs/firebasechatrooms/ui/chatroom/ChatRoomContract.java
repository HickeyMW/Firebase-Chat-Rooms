package com.wickeddevs.firebasechatrooms.ui.chatroom;

import com.wickeddevs.firebasechatrooms.data.model.Message;

public interface ChatRoomContract {

    interface View {

        void displayMessage(Message message);

        void toggleLoading(boolean isLoading);

        String getRoomName();

        String getUsername();
    }

    interface Presenter {

        void viewCreated(View createdView);

        void sendMessage(String body);

        void leaveChatRoom();
    }
}
