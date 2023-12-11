package com.itclimb.chatapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.itclimb.chatapp.model.ChatMessage;
import com.itclimb.chatapp.model.ChatGroup;
import com.itclimb.chatapp.repository.Repository;

import java.util.List;

public class ViewModelClass extends AndroidViewModel {
    Repository repository;

    public ViewModelClass(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void signUpAnonymousUser() {
        Application context = this.getApplication();
        repository.firebaseAnonymousAuth(context);
   }

   public String getCurrentUserId(){
        return repository.getCurrentUserID();
   }

   public void signOut() {
       repository.signOUT();
   }

   public MutableLiveData<List<ChatGroup>> getGroupList() {
        return repository.getChatGroupmMutableLiveData();
   }

   public void createNewGroup(String groupName) {
        repository.createNewChatGroup(groupName );
   }

   public MutableLiveData<List<ChatMessage >> getChatMessageLiveData (String groupName) {
        return repository.getChatMessageLiveData(groupName);
   }

    public void sendMessage(String msg, String chatGroup) {
        repository.sendMessage(msg, chatGroup  );
    }
}
