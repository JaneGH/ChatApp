package com.itclimb.chatapp.repository;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itclimb.chatapp.model.ChatMessage;
import com.itclimb.chatapp.model.ChatGroup;
import com.itclimb.chatapp.views.GroupsActivity;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    MutableLiveData<List<ChatGroup>> chatGroupmMutableLiveData;
    MutableLiveData<List<ChatMessage>> chatMessageLiveData;

    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference groupRef;


    public Repository() {
        this.chatGroupmMutableLiveData = new MutableLiveData<>();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        chatMessageLiveData = new MutableLiveData<>();

    }

    //Auth
    public  void  firebaseAnonymousAuth(Context context) {
        FirebaseAuth.getInstance().signInAnonymously()
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(context, GroupsActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    }
                });

    }

    public String getCurrentUserID() {
        return FirebaseAuth.getInstance().getUid();
    }

    public void signOUT () {
        FirebaseAuth.getInstance().signOut();
    }

    //Chat group

    public MutableLiveData<List<ChatGroup>> getChatGroupmMutableLiveData() {
        List<ChatGroup> groupList = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groupList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ChatGroup chatGroup = new ChatGroup(dataSnapshot.getKey());
                    groupList.add(chatGroup);

                    chatGroupmMutableLiveData.postValue(groupList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return chatGroupmMutableLiveData;
    }

    public void createNewChatGroup(String groupName) {
        reference.child(groupName).setValue(groupName);

    }

    public MutableLiveData<List<ChatMessage>> getChatMessageLiveData(String groupName) {
        groupRef = database.getReference().child(groupName);
        List <ChatMessage> messageList = new ArrayList<>();
        groupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ChatMessage message = dataSnapshot.getValue(ChatMessage.class);
                    messageList.add(message);
                }
                chatMessageLiveData.postValue(messageList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return chatMessageLiveData;
    }

    public void sendMessage(String messageText, String chatGroup ){
        DatabaseReference ref = database.getReference(chatGroup);
        if (!messageText.trim().equals("")){
            ChatMessage msg = new ChatMessage(
                    FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    messageText,
                    System.currentTimeMillis()
            );

            String messageKey = ref.push().getKey();
            ref.child(messageKey).setValue(msg);
        }

    }
}
