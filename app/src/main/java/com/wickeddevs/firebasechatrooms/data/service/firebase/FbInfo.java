package com.wickeddevs.firebasechatrooms.data.service.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.wickeddevs.firebasechatrooms.data.service.contract.UserService;

public class FbInfo implements UserService {

    public static FirebaseDatabase getDb() {
        return FirebaseDatabase.getInstance();
    }

    public static String getUid() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null){
            return currentUser.getUid();
        }
        return null;
    }

    @Override
    public void logInIfNotLoggedIn() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            firebaseAuth.signInAnonymously();
        }
    }
}
