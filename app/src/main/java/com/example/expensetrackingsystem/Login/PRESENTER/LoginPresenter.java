package com.example.expensetrackingsystem.Login.PRESENTER;

import com.example.expensetrackingsystem.Login.INTERFACES.LoginInterface;
import com.example.expensetrackingsystem.Login.MODEL.LoginModel;
import com.example.expensetrackingsystem.Login.VIEW.LoginView;

public class LoginPresenter implements LoginInterface.presenter {
    private LoginInterface.view view;
    private LoginModel model;

    public LoginPresenter(LoginView loginView) {
        this.view = loginView;
        model = new LoginModel();       //Initiating the model object

    }

    @Override
    public void signIn(String userEmail, String userPass) {
        //Check whether the userEmail and userPass is empty or not ??

        if (userEmail.isEmpty() || userPass.isEmpty()) {
            view.showToast("Fields cannot be empty!!");
        } else {
            model.signIn(userEmail, userPass, new LoginInterface.modelPresenterCallback() {
                @Override
                public void onSuccess(String msg) {
                    view.switchToHome();
                }

                @Override
                public void onError(String msg) {
                    view.showToast(msg);
                    view.dismissProgress();
                }
            });
        }
    }
}
