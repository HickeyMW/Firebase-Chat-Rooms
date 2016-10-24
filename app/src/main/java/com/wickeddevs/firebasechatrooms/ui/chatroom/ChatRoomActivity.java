package com.wickeddevs.firebasechatrooms.ui.chatroom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.wickeddevs.firebasechatrooms.R;
import com.wickeddevs.firebasechatrooms.adapters.MessageRVA;
import com.wickeddevs.firebasechatrooms.dagger.component.DaggerServiceComponent;
import com.wickeddevs.firebasechatrooms.dagger.component.DaggerViewInjectorComponent;
import com.wickeddevs.firebasechatrooms.data.model.Message;
import com.wickeddevs.firebasechatrooms.ui.home.HomeContract;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatRoomActivity extends AppCompatActivity implements ChatRoomContract.View {

    @Inject
    public ChatRoomContract.Presenter presenter;

    @InjectExtra String chatRoomName;
    @InjectExtra String username;

    @BindView(R.id.rvMessages) RecyclerView rvMessages;
    @BindView(R.id.etMessage) EditText etMessage;
    @BindView(R.id.btnSend) Button btnSend;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.vBottomShadow) View vBottomShadow;

    private MessageRVA messageRVA;
    private LinearLayoutManager linearLayoutManager;
    private boolean displayingShadow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        Dart.inject(this);
        ButterKnife.bind(this);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().sendMessage(etMessage.getText().toString());
                etMessage.setText("");
            }
        });
        setupRecyclerView();
        setTitle(chatRoomName);
        getPresenter().viewCreated(this);
    }

    private ChatRoomContract.Presenter getPresenter() {
        if(presenter == null){
            DaggerViewInjectorComponent.builder()
                    .serviceComponent(DaggerServiceComponent.create())
                    .build().inject(this);
        }
        return presenter;
    }

    @Override
    public void displayMessage(Message message) {
        messageRVA.addMessage(message);
    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            rvMessages.setVisibility(View.INVISIBLE);
            etMessage.setVisibility(View.INVISIBLE);
            btnSend.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            rvMessages.setVisibility(View.VISIBLE);
            etMessage.setVisibility(View.VISIBLE);
            btnSend.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public String getRoomName() {
        return chatRoomName;
    }

    @Override
    public String getUsername() {
        return username;
    }

    private void setupRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        rvMessages.setLayoutManager(linearLayoutManager);
        messageRVA = new MessageRVA();
        messageRVA.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = messageRVA.getItemCount();
                int lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 || lastVisiblePosition == friendlyMessageCount -2) {
                    rvMessages.scrollToPosition(positionStart);
                }
            }
        });
        rvMessages.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int friendlyMessageCount = messageRVA.getItemCount();
                int lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition != friendlyMessageCount -1) {
                    if (!displayingShadow) {
                        displayingShadow = true;
                        vBottomShadow.animate().alpha(1.0f).setDuration(300).start();
                    }
                } else {
                    if (displayingShadow) {
                        displayingShadow = false;
                        vBottomShadow.animate().alpha(0.0f).setDuration(300).start();
                    }
                }
            }
        });
        rvMessages.setAdapter(messageRVA);
    }
}
