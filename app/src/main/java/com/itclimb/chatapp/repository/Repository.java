package com.itclimb.chatapp.repository;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.itclimb.chatapp.views.GroupsActivity;

public class Repository {
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
}
