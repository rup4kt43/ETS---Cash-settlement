package com.example.expensetrackingsystem.SignUp.VIEW;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensetrackingsystem.Login.VIEW.LoginView;
import com.example.expensetrackingsystem.R;
import com.example.expensetrackingsystem.SignUp.INTERFACES.SignupInterface;
import com.example.expensetrackingsystem.SignUp.PRESENTER.SignupPresenter;

public class SignupView extends AppCompatActivity implements SignupInterface.view {

    private EditText et_username, et_useremail, et_userphone, et_password;
    private Button btn_signup;
    private SignupPresenter presenter;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_view);

        //Initiating the object of presenter
        presenter = new SignupPresenter(this);

        //Initiating the progressDialog
        progressDialog = new ProgressDialog(this);


        //Refrencing the widgets
        et_username = findViewById(R.id.et_userName);
        et_password = findViewById(R.id.et_userPassword);
        et_useremail = findViewById(R.id.et_userEmail);
        et_userphone = findViewById(R.id.et_userPhone);


        btn_signup = findViewById(R.id.btn_signUp);

        //Actions of widget
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Saving the input parameters to string
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                String email = et_useremail.getText().toString();
                String phone = et_userphone.getText().toString();

                //Showing the progress dialog
                showProgress("Please Wait......");

                //passing the input parameter to presenter for validation and signup
                presenter.signUp(username, password, email, phone);

            }
        });
    }



    //----------------IMPLEMENTED METHOD ( INTERFACE METHOD )-------------------------//
    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    //Setting empty error message
    @Override
    public void setUsernameEmptyErrorMessage(String username_field_cannot_be_empty) {
        et_username.setError(username_field_cannot_be_empty);
    }

    @Override
    public void setEmailEmptyErrorMessage(String useremail_field_cannot_be_empty) {
        et_useremail.setError(useremail_field_cannot_be_empty);
    }

    @Override
    public void setPhoneEmptyErrorMessage(String userphone_field_cannot_be_empty) {
        et_userphone.setError(userphone_field_cannot_be_empty);
    }

    @Override
    public void setPasswordEmptyErrorMessage(String userpass_field_cannot_be_empty) {
        et_password.setError(userpass_field_cannot_be_empty);
    }

    @Override
    public void switchToLogin() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissProgress();
                startActivity(new Intent(SignupView.this, LoginView.class));
            }
        }, 1000);

    }

    @Override
    public void showProgress(String msg) {
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    @Override
    public void dismissProgress() {
        progressDialog.dismiss();
    }


    //--------------------------------


}
