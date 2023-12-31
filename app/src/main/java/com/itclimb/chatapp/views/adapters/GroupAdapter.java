package com.itclimb.chatapp.views.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.itclimb.chatapp.R;
import com.itclimb.chatapp.databinding.ItemCardBinding;
import com.itclimb.chatapp.model.ChatGroup;
import com.itclimb.chatapp.views.ChatActivity;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    private ArrayList<ChatGroup> groupArrayList;

    public GroupAdapter(ArrayList<ChatGroup> groupArrayList) {
        this.groupArrayList = groupArrayList;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCardBinding itemCardBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                        R.layout.item_card,
                        parent,
                        false);
        return new GroupViewHolder(itemCardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        ChatGroup currentUser = groupArrayList.get(position);
        holder.itemCardBinding.setChatGroup(currentUser);

    }

    @Override
    public int getItemCount() {
        return groupArrayList.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder{
        private ItemCardBinding itemCardBinding;

        public GroupViewHolder(ItemCardBinding itemCardBinding) {
            super(itemCardBinding.getRoot());
            this.itemCardBinding = itemCardBinding;
            itemCardBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    ChatGroup clickedChatGroup = groupArrayList.get(position);
                    Intent intentCard = new Intent(view.getContext(), ChatActivity.class);
                    intentCard.putExtra("GROUP_NAME", clickedChatGroup.getGroupName());
                    view.getContext() .startActivity(intentCard );
                }
            });
        }
    }
}
