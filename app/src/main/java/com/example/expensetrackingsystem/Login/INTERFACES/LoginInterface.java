package com.example.expensetrackingsystem.Login.INTERFACES;

public interface LoginInterface {

    interface view {
        void showToast(String msg);

        void showProgress(String msg);

        void dismissProgress();

        void switchToHome();
    }

    interface presenter {
        void signIn(String userEmail, String userPass);
    }


    interface modelPresenterCallback {
        void onSuccess(String msg);

        void onError(String msg);
    }

}
