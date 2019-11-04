package com.example.expensetrackingsystem.SignUp.INTERFACES;

public interface SignupInterface {

    interface view {
        void showToast(String msg);

        void setUsernameEmptyErrorMessage(String username_field_cannot_be_empty);

        void setEmailEmptyErrorMessage(String useremail_field_cannot_be_empty);

        void setPhoneEmptyErrorMessage(String  userphone_field_cannot_be_empty);

        void setPasswordEmptyErrorMessage(String userpass_field_cannot_be_empty);

        void switchToLogin();

        void showProgress(String msg);

        void dismissProgress();

    }

    interface presenter {


        void signUp(String username, String password, String email, String phone);
    }

    interface presenterModelInterface {
        void onSuccessResponse();

        void onErrorResponse(String msg);
    }
}
