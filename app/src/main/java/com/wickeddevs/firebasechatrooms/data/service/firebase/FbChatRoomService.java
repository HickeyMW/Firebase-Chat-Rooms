package com.wickeddevs.firebasechatrooms.data.service.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.wickeddevs.firebasechatrooms.data.service.contract.ChatRoomService;

import java.util.ArrayList;
import java.util.Iterator;

public class FbChatRoomService implements ChatRoomService {

    @Override
    public void getChatRoomNames(final ChatRoomNamesCallback callback) {
        FbInfo.getDb().getReference().child("chatRoomNames").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> chatRoomNames = new ArrayList<String>();
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    chatRoomNames.add(iterator.next().getValue(String.class));
                }
                callback.onLoaded(chatRoomNames);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void sendMessage(String message, String chatRoomName) {

    }

    @Override
    public void joinChatRoom(String chatRoomName, String userName) {

    }

    @Override
    public void leaveChatRoom(String chatRoomName) {

    }
}
