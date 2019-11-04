package com.example.expensetrackingsystem.Login.VIEW;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensetrackingsystem.Home.VIEW.HomeView;
import com.example.expensetrackingsystem.Login.INTERFACES.LoginInterface;
import com.example.expensetrackingsystem.Login.PRESENTER.LoginPresenter;
import com.example.expensetrackingsystem.R;
import com.example.expensetrackingsystem.SignUp.VIEW.SignupView;

public class LoginView extends AppCompatActivity implements LoginInterface.view {
    LoginInterface.presenter presenter;
    EditText et_useremail,et_password;
    Button btn_login;
    TextView tv_signup;
    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initiating the presenter object
        presenter = new LoginPresenter(this);

        //Initiating the progress Dialog
        progressDialog = new ProgressDialog(this);

        //Refrencing the widgets
        et_useremail = findViewById(R.id.et_userEmail);
        et_password = findViewById(R.id.et_userPassword);
        btn_login = findViewById(R.id.btn_logIn);
        tv_signup = findViewById(R.id.tv_signUp);


        //action on widget
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //retrive the input text and save in string
                String userEmail = et_useremail.getText().toString();
                String userPass = et_password.getText().toString();

                //Showing the progress dialog
                showProgress("Please Wait.....");

                //Sending the retrived strings to presnter for business logic -> validation and signup
                presenter.signIn(userEmail,userPass);
            }
        });

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginView.this, SignupView.class));
            }
        });

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
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

    @Override
    public void switchToHome() {
        //Using a Thread to delay switch page by 1 second .i.e 1000 ms

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               startActivity(new Intent(LoginView.this, HomeView.class));
                dismissProgress();

                //switch to home
            }
        },1000);
    }
}
