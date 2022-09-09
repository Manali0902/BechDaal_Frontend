package com.example.bechdaal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {
    private Context context;
    private List<ChatList> chatList;

    public ChatListAdapter(Context context, List<ChatList> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chat_list_item, null);
        return new ChatListAdapter.ChatListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {
        ChatList chatLst = chatList.get(position);
        holder.chatName.setText(chatLst.getChatName());
        holder.chatNumber.setText(chatLst.getChatNumber());
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    class ChatListViewHolder extends RecyclerView.ViewHolder {

        TextView chatName, chatNumber;
        LinearLayout chatll;

        public ChatListViewHolder(@NonNull View itemView) {
            super(itemView);

            chatName = itemView.findViewById(R.id.chatName);
            chatNumber = itemView.findViewById(R.id.chatNumber);
            chatll = itemView.findViewById(R.id.chatll);

            chatll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //user, otheruser, otherusername
                    SessionManagement sessionManagement = new SessionManagement(itemView.getContext());
                    String user = sessionManagement.getSessionPhone();
                    String otherUser = chatNumber.getText().toString();
                    String otherUsername = chatName.getText().toString();
                    Intent i = new Intent(itemView.getContext(),ChatActivity.class);
                    i.putExtra("myusername",user);
                    i.putExtra("otherusername",otherUser);
                    i.putExtra("otherusernamename",otherUsername);
                    itemView.getContext().startActivity(i);
                }
            });

        }
    }

}
