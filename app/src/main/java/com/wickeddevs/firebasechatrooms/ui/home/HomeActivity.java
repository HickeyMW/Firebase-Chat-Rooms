package com.wickeddevs.firebasechatrooms.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wickeddevs.firebasechatrooms.R;
import com.wickeddevs.firebasechatrooms.adapters.ChatRoomsRVA;
import com.wickeddevs.firebasechatrooms.dagger.component.DaggerServiceComponent;
import com.wickeddevs.firebasechatrooms.dagger.component.DaggerViewInjectorComponent;
import com.wickeddevs.firebasechatrooms.ui.chatroom.Henson;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeContract.View, View.OnClickListener {

    @Inject
    public HomeContract.Presenter presenter;

    @BindView(R.id.rvChatRooms) RecyclerView rvChatRooms;

    ChatRoomsRVA chatRoomsRVA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        rvChatRooms.setLayoutManager(new LinearLayoutManager(this));
        getPresenter().viewCreated(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.miChangeName) {
            getPresenter().selectedChangeName();
        }
        return true;
    }

    @Override
    public void displayChatRooms(ArrayList<String> chatRoomNames) {
        chatRoomsRVA = new ChatRoomsRVA(chatRoomNames, this);
        rvChatRooms.setAdapter(chatRoomsRVA);
    }

    @Override
    public void displayChatRoomUi(String chatRoomName, String username) {
        Intent i = Henson.with(this).gotoChatRoomActivity().chatRoomName(chatRoomName).username(username).build();
        startActivity(i);
    }

    @Override
    public void displayChangeNameUi(final String username) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(R.string.change_username_title);
        View dialogLayout = getLayoutInflater().inflate(R.layout.alert_dialog_edit_text, null);
        final EditText editText = (EditText) dialogLayout.findViewById(R.id.editText);
        alertDialog.setView(dialogLayout);
        if (username == null) {
            alertDialog.setMessage(R.string.change_username_message_no_username);
        } else {
            editText.setText(username);
        }
        alertDialog.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getPresenter().changeName(editText.getText().toString());
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        int position = rvChatRooms.getChildAdapterPosition(v);
        String chatRoomName = chatRoomsRVA.getRoomName(position);
        getPresenter().selectedRoom(chatRoomName);
    }

    private HomeContract.Presenter getPresenter() {
        if(presenter == null){
            DaggerViewInjectorComponent.builder()
                    .serviceComponent(DaggerServiceComponent.create())
                    .build().inject(this);
        }
        return presenter;
    }
}
