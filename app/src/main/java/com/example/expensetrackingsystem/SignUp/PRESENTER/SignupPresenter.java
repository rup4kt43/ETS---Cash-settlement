package com.example.expensetrackingsystem.SignUp.PRESENTER;

import com.example.expensetrackingsystem.SignUp.INTERFACES.SignupInterface;
import com.example.expensetrackingsystem.SignUp.MODEL.SignUpModel;
import com.example.expensetrackingsystem.SignUp.VIEW.SignupView;

import java.util.logging.Handler;

public class SignupPresenter implements SignupInterface.presenter {
    private SignupInterface.view view;
    private SignUpModel model;

    public SignupPresenter(SignupView signupView) {
        this.view = signupView;
        model = new SignUpModel();
    }

    @Override
    public void signUp(String username, String password, String email, String phone) {

        //Checking the empty validation
        if (username.isEmpty()) {
            view.setUsernameEmptyErrorMessage("Username Field Cannot Be Empty");
        }
        if (password.isEmpty()) {
            view.setPasswordEmptyErrorMessage("Password Field Cannot Be Empty");
        }
        if (email.isEmpty()) {
            view.setEmailEmptyErrorMessage("Email Field Cannot Be Empty");
        }
        if (phone.isEmpty()) {
            view.setPhoneEmptyErrorMessage("Phone Field Cannot Be Empty");
        } else {
            //if non of the fields are empty then signup
            model.signup(username, password, email, phone, new SignupInterface.presenterModelInterface() {
                @Override
                public void onSuccessResponse() {

                    view.switchToLogin();
                }

                @Override
                public void onErrorResponse(String msg) {
                    view.showToast(msg);
                    view.dismissProgress();
                }
            });
        }
    }
}
