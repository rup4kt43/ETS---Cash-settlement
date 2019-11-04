package com.example.expensetrackingsystem.SignUp.MODEL;

import androidx.annotation.NonNull;

import com.example.expensetrackingsystem.Utilities.Global;
import com.example.expensetrackingsystem.SignUp.INTERFACES.SignupInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;

public class SignUpModel {
    public void signup(final String username, final String password, final String email, final String phone, final SignupInterface.presenterModelInterface callback) {

        Global.mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            callback.onSuccessResponse();
                            saveUserDetail(username,password,email,phone);
                        } else {
                            // If sign in fails, display a message to the user.
                            callback.onErrorResponse(task.getException().toString());

                        }

                    }
                });
    }

    private void saveUserDetail(String username, String password, String email, String phone) {
        //Refrencing to the node of user detail
        DatabaseReference ref_userDetail = Global.mDatabase.child("USER DETAIL");

        //Refrencing to the node of email
        DatabaseReference ref_useremail = ref_userDetail.child(String.valueOf(phone));

        //Refrencing to the node of username (child of email) and setting the value
        DatabaseReference ref_username = ref_useremail.child("userName");
        ref_username.setValue(username);

        //Refrencing to the node of userphone (child of email) and setting the value
        DatabaseReference ref_email = ref_useremail.child("userEmail");
        ref_email.setValue(email);


        //TODO REMAINING WORK



    }
}
