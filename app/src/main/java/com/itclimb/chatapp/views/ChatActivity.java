package com.itclimb.chatapp.views;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itclimb.chatapp.R;
import com.itclimb.chatapp.databinding.ActivityChatBinding;
import com.itclimb.chatapp.model.ChatMessage;
import com.itclimb.chatapp.viewModel.ViewModelClass;
import com.itclimb.chatapp.views.adapters.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;
    private ViewModelClass viewModel;
    private RecyclerView recyclerView;
    private ChatAdapter myAdapter;

    private List<ChatMessage> messageList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        viewModel = new ViewModelProvider(this).get(ViewModelClass.class );
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true );

        String groupName = getIntent().getStringExtra("GROUP_NAME");
        viewModel.getChatMessageLiveData(groupName).observe(this, new Observer<List<ChatMessage>>() {
            @Override
            public void onChanged(List<ChatMessage> chatMessages) {
                messageList = new ArrayList<>();
                messageList.addAll(chatMessages );

                myAdapter = new ChatAdapter(messageList,getApplicationContext());
                recyclerView.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();

                int latestPosition = myAdapter.getItemCount()-1;
                if (latestPosition>0){
                    recyclerView.smoothScrollToPosition(latestPosition);
                }
            }
        });

        binding.setVModel(viewModel);
        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = binding.edittextChatMessage.getText().toString();
                viewModel.sendMessage(msg, groupName);
                binding.edittextChatMessage.getText().clear();
            }
        });
    }
}
