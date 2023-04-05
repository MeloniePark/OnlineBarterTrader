package com.example.onlinebartertrader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatAdapter extends FirebaseRecyclerAdapter<Chat, ChatAdapter.ChatViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ChatAdapter(@NonNull FirebaseRecyclerOptions<Chat> options) {
        super(options);
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        inflates the item chat
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layout, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull Chat chat) {
//        if the user is logged into the app, the session username is equal to the chat message username,

        if (UserSession.getInstance().getUser() != null ) {
            if (UserSession.getInstance().getUser().equals(chat.getUsername())) {

//            hiding the any user layout and displaying current user layout
                holder.anyUserLL.setVisibility(View.GONE);
                holder.currentUserLL.setVisibility(View.VISIBLE);
                holder.currentUserNameTV.setText(chat.getUsername());
                holder.currentUserMessageTV.setText(chat.getChatMessage());
            } else {
//            hiding the current user layout and displaying the any user layout
                holder.currentUserLL.setVisibility(View.GONE);
                holder.anyUserLL.setVisibility(View.VISIBLE);
                holder.anyUserNameTV.setText(chat.getUsername());
                holder.anyUserMessageTV.setText(chat.getChatMessage());
            }
        }
    }
    //contains all the ids of all of the layouts and ui elements from the recycler view
    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout anyUserLL;
        private final TextView anyUserNameTV;
        private final TextView anyUserMessageTV;

        private final LinearLayout currentUserLL;
        private final TextView currentUserNameTV;
        private final TextView currentUserMessageTV;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            anyUserLL = itemView.findViewById(R.id.anyUserLayout);
            anyUserNameTV = itemView.findViewById(R.id.anyUserNameTextView);
            anyUserMessageTV = itemView.findViewById(R.id.anyUserMessageTextView);
            currentUserLL = itemView.findViewById(R.id.currentUserLayout);
            currentUserNameTV = itemView.findViewById(R.id.currentUserNameTextView);
            currentUserMessageTV = itemView.findViewById(R.id.currentUserMessageTextView);
        }
    }
}
