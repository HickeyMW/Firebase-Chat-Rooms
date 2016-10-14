package com.wickeddevs.firebasechatrooms.data.service.contract;

import com.wickeddevs.firebasechatrooms.data.model.Message;

import java.util.ArrayList;

public interface ChatRoomService {

    interface ChatRoomNamesCallback {

        void onLoaded(ArrayList<String> chatRoomNames);
    }

    interface ChatRoomMessagesListener {

        void newMessage(Message message);

        void initialMessagesLoaded();
    }

    void getChatRoomNames(ChatRoomNamesCallback callback);

    void sendMessage(String message, String chatRoomName);

    void joinChatRoom(String chatRoomName, String userName);

    void leaveChatRoom(String chatRoomName);
}
