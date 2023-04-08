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

/**
 * ChatAdapter.java
 *
 * Description: ChatAdapter class is one of the supporting class for the chat functionality for the
 *      receiver and provider
 */
public class ChatAdapter extends FirebaseRecyclerAdapter<Chat, ChatAdapter.ChatViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options firebase recycler option
     */
    public ChatAdapter(@NonNull FirebaseRecyclerOptions<Chat> options) {
        super(options);
    }

    /**
     * onCreateViewHolder(@NonNull ViewGroup parent, int viewType):
     *      On creation of the page, the item chat in the view gets inflated.
     *
     * @param parent view group's parent
     * @param viewType type of view
     * @return  returns new ChatViewHolder(view)
     */
    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflates the item chat
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layout, parent, false);
        return new ChatViewHolder(view);
    }

    /**
     * onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull Chat chat) :
     *      The user session is held and displays the current user's layout and
     *      hides when there's any users, then hides the current user's layout and displays layout
     *      for any users.
     *
     * @param holder view holder
     * @param position position of view
     * @param chat -
     */
    @Override
    protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull Chat chat) {
        //if the user is logged into the app, the session username is equal to the chat message username,

        if (UserSession.getInstance().getUser() != null ) {
            if (UserSession.getInstance().getUser().equals(chat.getUsername())) {

            //hiding the any user layout and displaying current user layout
                holder.anyUserLL.setVisibility(View.GONE);
                holder.currentUserLL.setVisibility(View.VISIBLE);
                holder.currentUserNameTV.setText(chat.getUsername());
                holder.currentUserMessageTV.setText(chat.getChatMessage());
            } else {
            //hiding the current user layout and displaying the any user layout
                holder.currentUserLL.setVisibility(View.GONE);
                holder.anyUserLL.setVisibility(View.VISIBLE);
                holder.anyUserNameTV.setText(chat.getUsername());
                holder.anyUserMessageTV.setText(chat.getChatMessage());
            }
        }
    }

    /**
     * ChatViewHolder Class
     *      Containing all the ids of all the layouts and UI elements form the recycler view
     */
    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout anyUserLL;
        private final TextView anyUserNameTV;
        private final TextView anyUserMessageTV;

        private final LinearLayout currentUserLL;
        private final TextView currentUserNameTV;
        private final TextView currentUserMessageTV;

        /**
         * ChatViewHolder(@NonNull View itemView)
         *      Gets the itemViews' layout components.
         * @param itemView view containing list of items.
         */
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
