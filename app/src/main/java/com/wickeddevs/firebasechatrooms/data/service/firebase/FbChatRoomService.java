package com.wickeddevs.firebasechatrooms.data.service.firebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.wickeddevs.firebasechatrooms.data.model.Message;
import com.wickeddevs.firebasechatrooms.data.service.contract.ChatRoomService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FbChatRoomService implements ChatRoomService, ChildEventListener {

    private ChatRoomMessagesListener listener;

    private DatabaseReference getChatRoomRef() {
        return FbInfo.getDb().getReference().child("chatRooms");
    }

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
    public void sendMessage(String message, String username, String chatRoomName) {
        HashMap hashMap = new HashMap();
        hashMap.put("username", username);
        hashMap.put("uid", FbInfo.getUid());
        hashMap.put("body", message);
        hashMap.put("timestamp", ServerValue.TIMESTAMP);
        getChatRoomRef().child(chatRoomName).child("messages").push().setValue(hashMap);
    }

    @Override
    public void joinChatRoom(String chatRoomName, String userName, final ChatRoomMessagesListener listener) {
        this.listener = listener;
        getChatRoomRef().child(chatRoomName).child("messages").addChildEventListener(this);
        getChatRoomRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.initialMessagesLoaded();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void leaveChatRoom(String chatRoomName) {
        getChatRoomRef().child(chatRoomName).removeEventListener(this);
    }



    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Message message = dataSnapshot.getValue(Message.class);
        if (message.uid != null) {
            if (message.uid.equals(FbInfo.getUid())) {
                message.isSentMessage = true;
            }
        }
        listener.newMessage(message);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
