package com.wickeddevs.firebasechatrooms.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wickeddevs.firebasechatrooms.R;
import com.wickeddevs.firebasechatrooms.data.model.Message;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MessageRVA extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Message> messages = new ArrayList<>();

    public MessageRVA() {
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).isSentMessage) {
            return 0;
        }
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_send, parent, false);
            return new ChatSendViewHolder(v);
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_receive, parent, false);
        return new ChatReceiveViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);

        if (holder instanceof ChatSendViewHolder) {
            ChatSendViewHolder vHolder = (ChatSendViewHolder) holder;
            vHolder.name.setText(message.username);
            vHolder.body.setText(message.body);
            vHolder.dateTime.setText(message.formattedTimestamp);
        } else {
            ChatReceiveViewHolder vHolder = (ChatReceiveViewHolder) holder;
            vHolder.name.setText(message.username);
            vHolder.body.setText(message.body);
            vHolder.dateTime.setText(message.formattedTimestamp);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addMessage(Message message) {
        messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }

    public class ChatSendViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.body) TextView body;
        @BindView(R.id.datetime) TextView dateTime;

        public ChatSendViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ChatReceiveViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.body) TextView body;
        @BindView(R.id.datetime) TextView dateTime;

        public ChatReceiveViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
