package com.example.bechdaal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private Context context;
    private List<Chat> chatList;

    public ChatAdapter(Context context, List<Chat> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chat_view, null);
        return new ChatAdapter.ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ChatViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        String textContent = chat.getText();
        Log.d("tttt","rrrrr:"+textContent);
//        holder.recievedChat.setText(textContent.substring(textContent.indexOf(" ")+1,textContent.length()));
        if (textContent.startsWith("Sender")){
            holder.sentChat.setText(textContent.substring(textContent.indexOf(" ")+1,textContent.length()));
            holder.recievedChat.setVisibility(View.GONE);
        }
        else if (textContent.startsWith("Reciever")){
            holder.recievedChat.setText(textContent.substring(textContent.indexOf(" ")+1,textContent.length()));
            holder.sentChat.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView sentChat,recievedChat;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            sentChat = itemView.findViewById(R.id.sent_chat);
            recievedChat = itemView.findViewById(R.id.recieved_chat);
        }
    }

}
