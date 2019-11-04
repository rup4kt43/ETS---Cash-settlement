package com.example.expensetrackingsystem.Login.MODEL;

import androidx.annotation.NonNull;

import com.example.expensetrackingsystem.Utilities.Global;
import com.example.expensetrackingsystem.Login.INTERFACES.LoginInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginModel {
    public void signIn(final String userEmail, String userPass, final LoginInterface.modelPresenterCallback callback) {
        Global.mAuth.signInWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Global.userEmail = userEmail;
                            callback.onSuccess("Succefully Logged In!!");
                        } else {
                            // If sign in fails, display a message to the user.
                            callback.onError(task.getException().toString());
                        }


                    }
                });
    }
}



