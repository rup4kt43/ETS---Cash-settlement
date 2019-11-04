package com.example.expensetrackingsystem.Utilities;

import android.app.ProgressDialog;
import android.content.Context;

public class GlobalProgressDialog {
    ProgressDialog progressDialog;


    public void showProgressDialog(String msg, Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    public void dismissProgress() {
        progressDialog.dismiss();
    }
}
