package com.wickeddevs.firebasechatrooms.ui.chatroom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.wickeddevs.firebasechatrooms.R;

public class ChatRoomActivity extends AppCompatActivity {

    @InjectExtra String chatRoomName;
    @InjectExtra String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        Dart.inject(this);
        ((EditText)findViewById(R.id.etMessage)).setText(chatRoomName);
    }
}
