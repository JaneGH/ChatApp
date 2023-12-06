package com.itclimb.chatapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.FirebaseApp;
import com.itclimb.chatapp.databinding.ActivityLoginBinding;
import com.itclimb.chatapp.viewModel.ViewModelClass;

public class LoginActivity extends AppCompatActivity {

    ViewModelClass viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //FirebaseApp.initializeApp(this);

        viewModel = new ViewModelProvider(this).get(ViewModelClass.class);
        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        activityLoginBinding.setVModel(viewModel );
    }
}