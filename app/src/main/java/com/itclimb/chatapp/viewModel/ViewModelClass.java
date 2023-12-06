package com.itclimb.chatapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.itclimb.chatapp.repository.Repository;

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
}
