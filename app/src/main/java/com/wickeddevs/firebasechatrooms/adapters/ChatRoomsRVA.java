package com.wickeddevs.firebasechatrooms.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatRoomsRVA extends RecyclerView.Adapter<ChatRoomsRVA.ChatRoomsVH> {

    private ArrayList<String> chatRooms;
    private View.OnClickListener listener;

    public ChatRoomsRVA(ArrayList<String> chatRooms, View.OnClickListener listener) {
        this.chatRooms = chatRooms;
        this.listener = listener;
    }

    @Override
    public ChatRoomsVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        v.setOnClickListener(listener);
        return new ChatRoomsVH(v);
    }

    @Override
    public void onBindViewHolder(ChatRoomsVH holder, int position) {
        holder.tvName.setText(chatRooms.get(position));
    }

    @Override
    public int getItemCount() {
        return chatRooms.size();
    }

    public String getRoomName(int position) {
        return chatRooms.get(position);
    }


    public class ChatRoomsVH extends RecyclerView.ViewHolder {
        @BindView(android.R.id.text1) TextView tvName;

        public ChatRoomsVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
